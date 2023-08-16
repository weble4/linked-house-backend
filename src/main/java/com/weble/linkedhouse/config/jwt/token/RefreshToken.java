package com.weble.linkedhouse.security.jwt.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @Column(name = "refresh_token_key")
    private String key;

    @Column(name = "refresh_token_value")
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }

    private RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static RefreshToken create(String key, String value) {
        return new RefreshToken(key, value);
    }
}
