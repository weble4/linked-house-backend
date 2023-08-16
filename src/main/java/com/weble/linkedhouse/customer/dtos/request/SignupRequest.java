package com.weble.linkedhouse.customer.dtos.request;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Set;

@Getter
public class SignupRequest {

    @Email(message = "올바른 Email 형식으로 입력해 주세요")
    @NotBlank(message = "Email 입력은 필수입니다.")
    private String customerEmail;

    @NotBlank(message = "패스워드 입력은 필수입니다.")
    private String customerPw;

    @NotNull
    private Set<Role> role;

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;

    @NotNull
    private String gender;

    @NotBlank(message = "휴대폰 번호 입력은 필수입니다.")
    private String phoneNum;

    @NotBlank(message = "생년월일 입력은 필수입니다.")
    private String birthDay;

    public void pwEncoding(String password) {
        this.customerPw = password;
    }

    private SignupRequest(String customerEmail, String customerPw, Set<Role> role, String nickname, String gender, String phoneNum, String birthDay) {
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.role = role;
        this.nickname = nickname;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.birthDay = birthDay;
    }

    public static SignupRequest of(String customerEmail, String customerPw, Set<Role> role,
                                   String nickname, String gender, String phoneNum, String birthDay) {
        return new SignupRequest(customerEmail, customerPw, role, nickname, gender, phoneNum, birthDay);
    }

    public Customer convertCustomer() {
        return Customer.of(customerEmail, customerPw, role);
    }

    public CustomerProfile convertProfile(Customer customer) {
        return CustomerProfile.of(customer, nickname, gender, birthDay, phoneNum, null);
    }

}
