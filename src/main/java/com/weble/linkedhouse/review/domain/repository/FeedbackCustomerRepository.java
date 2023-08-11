package com.weble.linkedhouse.review.domain.repository;

import com.weble.linkedhouse.review.domain.entity.FeedbackCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackCustomerRepository extends JpaRepository<FeedbackCustomer, Long> {

    Optional<List<FeedbackCustomer>> findAllByCustomerCustomerId(Long CustomerId);

    Optional<List<FeedbackCustomer>>findAllByHouseRentalId(Long rentalId);
}
