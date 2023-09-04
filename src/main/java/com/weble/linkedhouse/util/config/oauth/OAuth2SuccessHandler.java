package com.weble.linkedhouse.util.config.oauth;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.service.CustomerService;
import com.weble.linkedhouse.security.jwt.JwtTokenProvider;
import com.weble.linkedhouse.security.jwt.token.RefreshToken;
import com.weble.linkedhouse.security.jwt.token.RefreshTokenRepository;
import com.weble.linkedhouse.security.jwt.token.TokenDto;
import com.weble.linkedhouse.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.sql.Ref;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofMinutes(30);
    public static final String REDIRECT_PATH = "/articles";

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestBasedOnCookieRepository;
    private final CustomerService customerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Customer customer = customerService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        // RefreshToken 생성 -> 저장 -> 쿠키에 저장
        // 이미 jwtTokenProvider에 유효기간 정해져 있으므로 전달 X
        TokenDto refreshToken = jwtTokenProvider.generateToken(customer.getCustomerEmail());
        saveRefreshToken(customer.getCustomerEmail(), refreshToken.getRefreshToken());
        addRefreshTokenToCookie(request, response, refreshToken.getRefreshToken());

        // Access Token 생성 -> Path에 액세스 토큰 추가
        TokenDto accessToken = jwtTokenProvider.generateToken(customer.getCustomerEmail());
        String targetUrl = getTargetUrl(accessToken.getAccessToken());

        // 인증 관련 설정, 쿠키 제거
        clearAuthenticationAttributes(request, response);

        // 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 생성된 리프레시 토큰을 전달받아 DB 저장
    private void saveRefreshToken(String email, String newRefreshToken) {
        //
        RefreshToken refreshToken = refreshTokenRepository.findById(email)
                .map(entity -> entity.updateValue(newRefreshToken))
                .orElse(RefreshToken.create(email, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        //
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestBasedOnCookieRepository.removeAuthorizationRequest(request, response);
    }

    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }
}
