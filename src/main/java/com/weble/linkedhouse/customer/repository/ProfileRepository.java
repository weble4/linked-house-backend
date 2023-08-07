package com.weble.linkedhouse.customer.repository;

import com.weble.linkedhouse.customer.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<CustomerProfile, Long> {
}
