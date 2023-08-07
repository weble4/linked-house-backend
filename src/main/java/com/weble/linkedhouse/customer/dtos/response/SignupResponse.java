package com.weble.linkedhouse.customer.dtos.response;

import lombok.Getter;

@Getter
public class SignupResponse {

    private String customerEmail;

    private String response;

    public SignupResponse(String customerEmail) {
        this.customerEmail = customerEmail;
        this.response = "Email 인증을 완료해야 사용 가능 합니다.";
    }
}
