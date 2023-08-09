package com.weble.linkedhouse.security.jwt;

import com.weble.linkedhouse.security.jwt.token.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties properties;
    private final UserDetailsService userDetailsService;

    private final Long accessTokenValidTime = 30 * 60 * 1000L;  // 30분
    private final Long refreshTokenValidTime = 14 * 24 * 60 * 60 * 1000L;  // 2주

    public TokenDto generateToken(String customerEmail) {

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setIssuer(properties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .claim("customerEmail", customerEmail)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.of(accessToken, refreshToken, accessTokenValidTime);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getCustomerEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getCustomerEmail(String token) {
        Claims claims= getClaims(token);
        return claims.get("customerEmail", String.class);
    }

    public JwtReturn validToken(String token) {
        log.info("valid jwt token : {}", token);
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.after(new Date()) ?
                    JwtReturn.SUCCESS : JwtReturn.FAIL;
        } catch (ExpiredJwtException e) {
            log.error("jwt token 만료", e);
            return JwtReturn.EXPIRED;
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.", e);
        } catch (MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.", e);
        } catch (Exception e) {
            log.error("오류 입니다", e);
        }
        return JwtReturn.FAIL;
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(properties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
