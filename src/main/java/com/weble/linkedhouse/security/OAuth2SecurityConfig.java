package com.weble.linkedhouse.security;

import com.weble.linkedhouse.customer.service.CustomerService;
import com.weble.linkedhouse.security.jwt.JwtAuthenticationFilter;
import com.weble.linkedhouse.security.jwt.JwtTokenProvider;
import com.weble.linkedhouse.security.jwt.token.RefreshTokenRepository;
import com.weble.linkedhouse.util.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.weble.linkedhouse.util.config.oauth.OAuth2SuccessHandler;
import com.weble.linkedhouse.util.config.oauth.OAuth2UserCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/*
@RequiredArgsConstructor
@Configuration
public class OAuth2SecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerService customerService;

    @Bean
    public WebSecurityCustomizer configure() { // Spring Security ignore
        return web -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/api/login"); // Todo: 모든 Web URL 에 적용할지 Login 단에만 적용할지 고민중
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Token 방식 인증 위해 Form Login, Session Ignore
        http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Header 확인 할 커스텀 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // Token 재발급 URL 은 인증 없이 접근가능. 나머지 API URL 은 인증 필요
        http.authorizeRequests() // Todo: Deprecated ?
                .requestMatchers("/api/token")
                .permitAll()
                .requestMatchers("/api/**")
                .authenticated()
                .anyRequest()
                .permitAll();

        http.oauth2Login()
                .loginPage("/api/login")
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .successHandler(oAuth2SuccessHandler())
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);

        http.logout()
                .logoutSuccessUrl("/login"); // Todo: login 단으로 보내주는게 맞는지 체크

        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                        , new AntPathRequestMatcher("/api/**"));

        return http.build();
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    @Lazy
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                customerService
        );
    }

    @Bean
    public JwtAuthenticationFilter tokenAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider);
    }
}


 */