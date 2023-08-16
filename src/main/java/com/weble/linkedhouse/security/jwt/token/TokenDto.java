package com.weble.linkedhouse.security.jwt.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class TokenDto {

    private String accessToken;
    @JsonIgnore
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
