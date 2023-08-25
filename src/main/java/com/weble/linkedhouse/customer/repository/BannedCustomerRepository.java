package com.weble.linkedhouse.customer.repository;

import com.weble.linkedhouse.customer.entity.BannedCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannedCustomerRepository extends JpaRepository<BannedCustomer, Long> {

}

