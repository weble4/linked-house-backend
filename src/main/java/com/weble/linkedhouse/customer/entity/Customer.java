package com.weble.linkedhouse.customer.entity;

import com.weble.linkedhouse.customer.entity.constant.AuthState;
import com.weble.linkedhouse.customer.entity.constant.Banneduser;
import com.weble.linkedhouse.customer.entity.constant.DeleteRequest;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "customer_email")
})
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

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Set<Role> role = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_state", nullable = false,
            columnDefinition = "varchar(30) default 'non_auth'")
    private AuthState authState;

    @Column(name = "suspended")
    private Banneduser suspended;

    @ToString.Exclude
    @OneToOne(mappedBy = "customer")
    private CustomerProfile customerProfile;

    private Customer(String customerEmail, String customerPw, Set<Role> role) {
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.deleteRequest = DeleteRequest.NOT_DELETE;
        this.role = role;
        this.authState= AuthState.NONAUTH;
        this.suspended = Banneduser.ACTIVE;
    }

    public static Customer of(String customerEmail, String customerPw, Set<Role> role) {
        return new Customer(customerEmail, customerPw, role);
    }

    public void approveAuth(AuthState authState) {
        this.authState= authState;
    }

    public void changePassword(String customerPw) {
        this.customerPw = customerPw;
    }

    public void deleteAccountRequest() {
        this.deleteRequest = DeleteRequest.DELETE;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
        customerProfile.setCustomer(this);
    }

    public void addRole(Role newRole) {
        Set<Role> updatedRoles = new HashSet<>(this.role);
        updatedRoles.add(newRole);
        this.role = updatedRoles;
    }
    public void banned() {
        this.suspended = Banneduser.SUSPENDED;
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
