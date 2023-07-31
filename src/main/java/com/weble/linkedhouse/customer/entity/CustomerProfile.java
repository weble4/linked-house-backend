package com.weble.linkedhouse.customer.entity;

import com.weble.linkedhouse.customer.entity.constant.PublicAt;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_id")
    private Long profileId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    @Column(name="customer_id", nullable = false)
    private Customer customerId;

    @Column(name="nickname")
    private String nickname;

    @Column(name="gender")
    private String gender;

    @Column(name="birth_date")
    private String birthDate;

    @Column(name="phone_num")
    private String phoneNum;

    @Column(name="public_at")
    @Enumerated(EnumType.STRING)
    private PublicAt publicAt;

}
