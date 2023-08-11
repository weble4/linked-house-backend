package com.weble.linkedhouse.customer.dtos.response;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.security.jwt.token.TokenDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private Long customerId;
    private String customerEmail;
    private String customerPw;
    private String nickname;
    private String phoneNum;
    private String birthDay;
    private TokenDto token;

    @Builder
    private LoginResponse(Long customerId, String customerEmail, String customerPw, String nickname, String phoneNum, String birthDay, TokenDto token) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.birthDay = birthDay;
        this.token = token;
    }

    public static LoginResponse of(Customer customer, CustomerProfile customerProfile, TokenDto token) {
        return LoginResponse.builder()
                .customerId(customer.getCustomerId())
                .customerEmail(customer.getCustomerEmail())
                .customerPw(customer.getCustomerPw())
                .nickname(customerProfile.getNickname())
                .phoneNum(customerProfile.getPhoneNum())
                .birthDay(customerProfile.getBirthDay())
                .token(token)
                .build();
    }
}
