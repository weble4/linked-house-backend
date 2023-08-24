package com.weble.linkedhouse.customer.entity;

import com.weble.linkedhouse.customer.entity.constant.Banneduser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannedCustomer {

    @Id
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "suspended_date")
    private LocalDateTime suspendedDate;

    private Banneduser suspend;

    private BannedCustomer(Long customerId){
        this.customerId = customerId;
        this.suspendedDate = LocalDateTime.now();
        this.suspend = Banneduser.SUSPENDED;
    }

    public static BannedCustomer of(Long customerId) {
        return new BannedCustomer(customerId);
    }
}
