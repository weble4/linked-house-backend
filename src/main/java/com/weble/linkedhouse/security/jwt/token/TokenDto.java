package com.weble.linkedhouse.security.jwt.token;

import lombok.Getter;

@Getter
public class TokenDto {

    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;


    private TokenDto(String accessToken, String refreshToken, Long accessTokenExpiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

    public static TokenDto of(String accessToken, String refreshToken, Long accessTokenExpiresIn) {
        return new TokenDto(accessToken, refreshToken, accessTokenExpiresIn);
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
