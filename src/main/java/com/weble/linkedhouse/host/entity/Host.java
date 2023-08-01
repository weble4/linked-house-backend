package com.weble.linkedhouse.host.entity;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.host.entity.constant.ApproveState;
import com.weble.linkedhouse.host.entity.constant.LockState;
import com.weble.linkedhouse.house.entity.House;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Host {

    @Id
    @Column(name = "host_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hostId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Column(name = "approve_state")
    @Enumerated(EnumType.STRING)
    private ApproveState approvestate;  // approve , not_approve

    @Column(name = "lock_state")
    @Enumerated(EnumType.STRING)
    private LockState lockState;   // stop, live

    @ToString.Exclude
    @OneToMany(mappedBy = "host")
    private List<House> houses = new ArrayList<>();


    @Builder
    private Host(Customer customer, ApproveState approvestate, LockState lockState) {
        this.customer = customer;
        this.approvestate = approvestate;
        this.lockState = lockState;
    }

    public static Host of(Customer customer, ApproveState approvestate, LockState lockState) {
        return new Host(customer, approvestate, lockState);
    }

    public void addHouse(House house) {
        this.getHouses().add(house);
    }

    public void addHouses(Collection<House> houses) {
        this.getHouses().addAll(houses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Host that)) return false;
        return this.getHostId() != null && getHostId().equals(that.getHostId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHostId());
    }
}
