package com.weble.linkedhouse.customer.entity;

import com.weble.linkedhouse.customer.dtos.request.UpdateRequest;
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

    @Column(length = 100, nullable = false)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String gender;

    @Column(name="birth_day", nullable = false, length = 100)
    private String birthDay;

    @Column(name="phone_num", nullable = false, length = 100)
    private String phoneNum;

    @Column(name="public_at", nullable = false,
            columnDefinition = "varchar(50) default 'public'")
    @Enumerated(EnumType.STRING)
    private PublicAt publicAt;

    @Column(name = "profile_image")
    private String imagePath;

    private CustomerProfile(Customer customer, String nickname, String gender, String birthDay, String phoneNum, String imagePath) {
        this.customer = customer;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDay = birthDay;
        this.phoneNum = phoneNum;
        this.publicAt = PublicAt.PUBLIC;
        this.imagePath = imagePath;
    }
    public static CustomerProfile of(Customer customer, String nickname, String gender, String birthDay, String phoneNum, String imagePath) {
        return new CustomerProfile(customer, nickname, gender, birthDay, phoneNum, imagePath);
    }

    public void changePublicAt(PublicAt publicAt) {
        this.publicAt = publicAt;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void updateProfile(UpdateRequest updateRequest, String imagePath) {
        this.nickname = updateRequest.getNickname() !=null ? updateRequest.getNickname() : nickname;
        this.phoneNum = updateRequest.getPhoneNum() != null ? updateRequest.getPhoneNum() : phoneNum;
        this.publicAt = updateRequest.getPublicAt() != null ? updateRequest.getPublicAt() : publicAt;
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerProfile that)) return false;
        return this.getProfileId() != null && getProfileId().equals(that.getProfileId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProfileId());
    }
}
