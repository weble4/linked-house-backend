package com.weble.linkedhouse.customer.repository;

import com.weble.linkedhouse.customer.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<CustomerProfile, Long> {

    Optional<CustomerProfile> findByCustomerCustomerId(Long customerId);
    Optional<CustomerProfile> findByCustomerCustomerEmail(String customerEmail);
}
