package com.weble.linkedhouse.review.repository;

import com.weble.linkedhouse.review.entity.FeedbackCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackCustomerRepository extends JpaRepository<FeedbackCustomer, Long> {

    Page<FeedbackCustomer> findAllByCustomerCustomerId(Long customerId, Pageable pageable);

    Page<FeedbackCustomer> findAllByHouseRentalId(Long rentalId, Pageable pageable);
}