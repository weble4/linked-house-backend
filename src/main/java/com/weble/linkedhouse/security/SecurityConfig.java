package com.weble.linkedhouse.security;


import com.weble.linkedhouse.security.jwt.JwtAuthenticationFilter;
import com.weble.linkedhouse.security.jwt.JwtTokenProvider;
import com.weble.linkedhouse.util.RequestMatcherBuilder;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private final String[] PERMIT_URL = {
            "/actuator/**",
            "/customer/signup",
            "/customer/activate-state",
    };
    private final String[] docsUrl = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api-docs/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, RequestMatcherBuilder mvc) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions().sameOrigin())    // H2 콘솔 사용을 위한 설정
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers( mvc.matchers(docsUrl)).permitAll()
                        .requestMatchers( mvc.matchers(PERMIT_URL)).permitAll()
                        .requestMatchers(toStaticResources().atCommonLocations(), toH2Console()).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logoutConfig -> logoutConfig
                        .invalidateHttpSession(true)
                )
                .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception{

        AuthenticationManagerBuilder authManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        authManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authManagerBuilder.build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Filter tokenFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}

