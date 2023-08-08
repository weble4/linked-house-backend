package com.weble.linkedhouse.review.repository;

import com.weble.linkedhouse.review.entity.FeedbackCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackCustomerRepository extends JpaRepository<FeedbackCustomer, Long> {

    Optional<FeedbackCustomer> findByCustomerCustomerId(String CustomerId);

}
