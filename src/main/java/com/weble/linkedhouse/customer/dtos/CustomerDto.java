package com.weble.linkedhouse.customer.dtos;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.constant.Role;
import lombok.Getter;

import java.util.Set;

@Getter
public class CustomerDto {

    private Long customerId;

    private String customerEmail;

    private String customerPw;

    private Set<Role> role;

    private CustomerDto(Long customerId, String customerEmail, String customerPw, Set<Role> role) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.role = role;
    }

    public static CustomerDto of(Long customerId, String customerEmail, String customerPw, Set<Role> role) {
        return new CustomerDto(customerId, customerEmail, customerPw, role);
    }

    public static CustomerDto from(Customer entity) {
        return new CustomerDto(
                entity.getCustomerId(),
                entity.getCustomerEmail(),
                entity.getCustomerPw(),
                entity.getRole()
        );
    }

    public Customer toEntity() {
        return Customer.of(customerEmail, customerPw, role);
    }
}