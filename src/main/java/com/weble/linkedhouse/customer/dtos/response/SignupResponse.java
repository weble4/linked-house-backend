package com.weble.linkedhouse.customer.dtos.response;

import lombok.Getter;

@Getter
public class SignupResponse {

    private String customerEmail;

    private String response;

    public SignupResponse(String customerEmail) {
        this.customerEmail = customerEmail;
        this.response = "인증 Email 발송 하였습니다. Email 인증 후 사용이 가능합니다.";
    }
}
