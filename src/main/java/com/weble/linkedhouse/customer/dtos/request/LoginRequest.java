package com.weble.linkedhouse.customer.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "이메일을 입력해주세요")
    private String customerEmail;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String customerPw;

    public LoginRequest(String customerEmail, String customerPw) {
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
    }
}
