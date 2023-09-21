package com.weble.linkedhouse.security;


import com.weble.linkedhouse.customer.service.CustomerService;
import com.weble.linkedhouse.security.jwt.JwtAuthenticationFilter;
import com.weble.linkedhouse.security.jwt.JwtTokenProvider;
import com.weble.linkedhouse.security.jwt.token.RefreshTokenRepository;
import com.weble.linkedhouse.util.RequestMatcherBuilder;
import com.weble.linkedhouse.util.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.weble.linkedhouse.util.config.oauth.OAuth2SuccessHandler;
import com.weble.linkedhouse.util.config.oauth.OAuth2UserCustomService;
import com.weble.linkedhouse.util.config.oauth.provider.OAuth2Provider;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerService customerService;


    private final String[] docsUrl = {
            "/actuator/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api-docs/**",
    };

    private final String[] permitUrl = {
            "/api/customers/login",
            "/api/customers/signup",
            "/api/customers/activate-state",
            "/api/customers/check-email",
            "/api/customers/reissue",
            "/api/houses",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, RequestMatcherBuilder mvc) throws Exception {
        return http
                .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions().sameOrigin())    // H2 콘솔 사용을 위한 설정
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(mvc.matchers(permitUrl)).permitAll()
                        .requestMatchers(mvc.matchers(docsUrl)).permitAll()
                        .requestMatchers(toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(mvc.matchers("/api/admin/*")).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/api/login/oauth/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService)
                .and()
                .successHandler(oAuth2SuccessHandler())
                .and()

                .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public Filter tokenFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("https://localhost:3000");
        configuration.addAllowedOrigin("http://localhost");
        configuration.addAllowedOrigin("https://localhost");
        configuration.addAllowedOrigin("http://10.0.1.6");
        configuration.addAllowedOrigin("https://10.0.1.6");
        configuration.addAllowedOrigin("http://10.0.1.6:3000");
        configuration.addAllowedOrigin("https://10.0.1.6:3000");
        configuration.addAllowedOrigin("http://110.165.18.244");
        configuration.addAllowedOrigin("https://110.165.18.244");
        configuration.addAllowedOrigin("http://110.165.18.244:3000");
        configuration.addAllowedOrigin("https://110.165.18.244:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;

    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    @Lazy
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(jwtTokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                customerService
        );
    }

    @Bean
    public JwtAuthenticationFilter tokenAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = Arrays.asList(
                createClientRegistration(OAuth2Provider.KAKAO),
                createClientRegistration(OAuth2Provider.NAVER)
        );

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration createClientRegistration(OAuth2Provider provider) {
        return ClientRegistration.withRegistrationId(provider.getRegistrationId())
                .clientId("${" + provider.getRegistrationId() + "_client_id}")
                .clientSecret("${" + provider.getRegistrationId() + "_client_secret}")
                .redirectUri("/api/login/")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile_nickname", "profile_image", "account_email")
                .authorizationUri(provider.getAuthorizationUri())
                .tokenUri(provider.getTokenUri())
                .userNameAttributeName(provider.getUserNameAttributeName())
                .build();
    }
}

