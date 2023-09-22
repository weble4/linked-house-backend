package com.weble.linkedhouse.exception.response;


import lombok.Getter;

public enum ErrorMessage {

    CUSTOMER_NOT_FOUND("아이디/또는 비밀번호가 틀렸습니다."),
    NO_AUTHORIZATION("인증이 필요 합니다."),
    JWT_EXCEPTION("JWT 오류입니다!"),
    DUPLICATED_CUSTOMER("이미 가입된 이메일입니다"),
    HOUSE_NOT_FOUND("존재하지 않는 장소입니다."),
    DELETE_CUSTOMER("삭제 신청한 아이디입니다."),
    ALREADY_HAS_ROLE("이미 호스트 자격이 있습니다."),
    ALREADY_AUTHORIZATION("이미 인증된 이메일입니다."),
    ALREADY_PAY_RESERVATION("이미 결제된 예약입니다."),
    NOT_FOUND_CUSTOMER("존재하지 않는 회원입니다."),
    NOT_FOUND_NOTIFICATION("존재하지 않는 알람입니다."),
    NOT_FOUND_ROOM("존재 하지 않는 방입니다."),
    NOT_FOUND_PAYMENT("존재 하지 않는 결제입니다."),
    NOT_FOUND_REVIEW("존재하지 않는 리뷰입니다."),
    NOT_FOUND_RESERVATION("존재하지 않는 예약입니다."),
    CAN_NOT_REPORT("다른 사람에 대한 리뷰는 신고할 수 없습니다."),
    ALREADY_PERMIT_RESERVATION("이미 허가된 예약입니다");

   @Getter
   private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
