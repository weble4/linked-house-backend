package com.weble.linkedhouse.customer.entity;

import com.weble.linkedhouse.customer.entity.constant.DeleteRequest;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(nullable = false, name = "customer_email")
    private String customerEmail;

    @Column(nullable = false, name = "customer_pw")
    private String customerPw;

    @Column(name = "delete_request")
    @Enumerated(EnumType.STRING)
    private DeleteRequest deleteRequest;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY)
    private CustomerProfile customerProfile;

    private Customer(String customerEmail, String customerPw, DeleteRequest deleteRequest, Role role) {
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.deleteRequest = deleteRequest;
        this.role = role;
    }

    public static Customer of(String customerEmail, String customerPw, DeleteRequest type, Role role) {
        return new Customer(customerEmail, customerPw, type, role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer that)) return false;
        return this.getCustomerId() != null && getCustomerId().equals(that.getCustomerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId());
    }
}
