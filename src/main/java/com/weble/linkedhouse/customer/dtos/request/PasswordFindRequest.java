package com.weble.linkedhouse.customer.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordFindRequest {

    private String customerEmail;
    private String customerPw;

    public PasswordFindRequest(String customerEmail, String customerPw) {
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
    }

}
