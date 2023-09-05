package com.weble.linkedhouse.util.config.oauth.provider;

public enum OAuth2Provider {
    KAKAO("kakao", "https://kauth.kakao.com/oauth/authorize", "https://kauth.kakao.com/oauth/token", "id"),
    NAVER("naver", "https://nid.naver.com/oauth2.0/authorize", "https://nid.naver.com/oauth2.0/token", "response");

    private final String registrationId;
    private final String authorizationUri;
    private final String tokenUri;
    private final String userNameAttributeName;

    OAuth2Provider(String registrationId, String authorizationUri, String tokenUri, String userNameAttributeName) {
        this.registrationId = registrationId;
        this.authorizationUri = authorizationUri;
        this.tokenUri = tokenUri;
        this.userNameAttributeName = userNameAttributeName;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getAuthorizationUri() {
        return authorizationUri;
    }

    public String getTokenUri() {
        return tokenUri;
    }

    public String getUserNameAttributeName() {
        return userNameAttributeName;
    }
}

