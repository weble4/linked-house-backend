package com.weble.linkedhouse.review.repository;

import com.weble.linkedhouse.review.entity.FeedbackCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackCustomerRepository extends JpaRepository<FeedbackCustomer, Long> {

    List<FeedbackCustomer> findAllByCustomerCustomerId(Long customerId);

    List<FeedbackCustomer> findAllByHouseRentalId(Long rentalId);
}