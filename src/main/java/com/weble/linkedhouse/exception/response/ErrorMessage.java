package com.weble.linkedhouse.exception.response;


import lombok.Getter;

public enum ErrorMessage {

    CUSTOMER_NOT_FOUND("아이디/또는 비밀번호가 틀렸습니다."),
    NO_AUTHORIZATION("인증이 필요한 아이디입니다."),
    NOT_FOUND_CUSTOMER("존재하지 않는 회원입니다."),
    JWT_NOT_NULL("JWT Token can't be Null or Empty"),
    DUPLICATED_CUSTOMER("이미 가입된 이메일입니다"),
    HOUSE_NOT_FOUND("존재하지 않는 장소입니다."),
    ALREADY_HAS_ROLE("이미 호스트 자격이 있습니다."),
    DELETE_CUSTOMER("삭제 신청한 아이디입니다."),
    ALREADY_AUTHORIZATION("이미 인증된 이메일입니다."),
    NOT_FOUND_NOTIFICATION("존재하지 않는 알람입니다.");

   @Getter
   private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
