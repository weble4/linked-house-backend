package com.weble.linkedhouse.customer.entity;

import com.weble.linkedhouse.customer.entity.constant.AuthState;
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

    @Column(name = "delete_request",
            columnDefinition = "varchar(50) default 'no'")
    @Enumerated(EnumType.STRING)
    private DeleteRequest deleteRequest;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_state", nullable = false,
            columnDefinition = "varchar(30) default 'non_auth'")
    private AuthState authState;


    @ToString.Exclude
    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY)
    private CustomerProfile customerProfile;

    private Customer(String customerEmail, String customerPw, Role role) {
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.deleteRequest = DeleteRequest.NOT_DELETE;
        this.role = role;
        this.authState= AuthState.NONAUTH;
    }

    public static Customer of(String customerEmail, String customerPw, Role role) {
        return new Customer(customerEmail, customerPw, role);
    }

    public void ApproveAuth(AuthState authState) {
        this.authState= authState;
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
