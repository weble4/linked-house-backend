package com.weble.linkedhouse.customer.dtos.response;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.security.jwt.token.TokenDto;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class LoginResponse {

    private Long customerId;
    private String customerEmail;
    private String customerPw;
    private String nickname;
    private String phoneNum;
    private String birthDay;
    private Set<String> role;
    private TokenDto token;

    private LoginResponse(Long customerId, String customerEmail, String customerPw, String nickname,
                          String phoneNum, String birthDay, Set<String> role, TokenDto token) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.birthDay = birthDay;
        this.role = role;
        this.token = token;
    }

    public static LoginResponse of(Customer customer, CustomerProfile customerProfile, TokenDto token) {
        return new LoginResponse(
                customer.getCustomerId(),
                customer.getCustomerEmail(),
                customer.getCustomerPw(),
                customerProfile.getNickname(),
                customerProfile.getPhoneNum(),
                customerProfile.getBirthDay(),
                customer.getRole().stream().map(Role::getReason)
                        .collect(Collectors.toSet()),
                token);
    }
}
