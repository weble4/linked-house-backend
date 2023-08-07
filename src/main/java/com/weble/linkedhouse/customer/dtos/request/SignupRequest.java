package com.weble.linkedhouse.customer.dtos.request;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotNull
    @Email
    private String customerEmail;

    @NotNull
    private String customerPw;

    @NotNull
    private Role role;

    @NotNull
    private String nickname;

    @NotNull
    private String gender;

    @NotNull
    private String phoneNum;

    @NotNull
    private String birthDate;

    private SignupRequest(String customerEmail, String customerPw, Role role, String nickname, String gender, String phoneNum, String birthDate) {
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.role = role;
        this.nickname = nickname;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.birthDate = birthDate;
    }

    public static SignupRequest of(String customerEmail, String customerPw, Role role, String nickname, String gender, String phoneNum, String birthDate) {
        return new SignupRequest(customerEmail, customerPw, role, nickname, gender, phoneNum, birthDate);
    }

    public Customer convertCustomer() {
        return Customer.of(customerEmail, customerPw, role);
    }

    public CustomerProfile convertProfile(Customer customer) {
        return CustomerProfile.of(customer, nickname, gender, birthDate, phoneNum);
    }
}
