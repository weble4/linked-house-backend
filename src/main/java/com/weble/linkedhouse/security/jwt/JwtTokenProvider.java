package com.weble.linkedhouse.security.jwt;

import com.weble.linkedhouse.security.jwt.token.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties properties;

    private final Long accessTokenValidTime = 30 * 60 * 1000L;  // 30분
    private final Long refreshTokenValidTime = 14 * 24 * 60 * 60 * 1000L;  // 2주

    public TokenDto generateToken(String customerId) {

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setIssuer(properties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .claim("customerId", customerId)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.of(accessToken, refreshToken, accessTokenValidTime);
    }


    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(properties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
