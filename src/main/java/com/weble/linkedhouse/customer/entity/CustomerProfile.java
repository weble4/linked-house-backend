package com.weble.linkedhouse.customer.entity;

import com.weble.linkedhouse.customer.entity.constant.PublicAt;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class CustomerProfile extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_id")
    private Long profileId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    private String nickname;

    @Column(nullable = false)
    private String gender;

    @Column(name="birth_date", nullable = false)
    private String birthDate;

    @Column(name="phone_num", nullable = false)
    private String phoneNum;

    @Column(name="public_at", nullable = false,
            columnDefinition = "VARCHAR(50) DEFAULT 'PUBLIC'")
    @Enumerated(EnumType.STRING)
    private PublicAt publicAt;

    private CustomerProfile(Customer customer, String nickname, String gender, String birthDate, String phoneNum) {
        this.customer = customer;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
    }
    public static CustomerProfile of(Customer customer, String nickname, String gender, String birthDate, String phoneNum) {
        return new CustomerProfile(customer, nickname, gender, birthDate, phoneNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerProfile that)) return false;
        return getProfileId().equals(that.getProfileId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProfileId());
    }
}
