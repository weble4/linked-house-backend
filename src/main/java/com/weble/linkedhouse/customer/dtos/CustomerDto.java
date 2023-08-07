package com.weble.linkedhouse.customer.dtos;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.constant.Role;
import lombok.Getter;

@Getter
public class CustomerDto {

    private String customerEmail;

    private String customerPw;

    private Role role;

    private CustomerDto(String customerEmail, String customerPw, Role role) {
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.role = role;
    }

    public static CustomerDto of(String customerEmail, String customerPw, Role role) {
        return new CustomerDto(customerEmail, customerPw, role);
    }

    public static CustomerDto from(Customer entity) {
        return new CustomerDto(
                entity.getCustomerEmail(),
                entity.getCustomerPw(),
                entity.getRole()
        );
    }

    public Customer toEntity() {
        return Customer.of(customerEmail, customerPw, role);
    }
}