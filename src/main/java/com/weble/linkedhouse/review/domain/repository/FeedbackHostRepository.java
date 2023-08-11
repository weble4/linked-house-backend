package com.weble.linkedhouse.review.domain.repository;

import com.weble.linkedhouse.review.domain.entity.FeedbackHost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackHostRepository extends JpaRepository<FeedbackHost, Long> {

    Optional<List<FeedbackHost>> findAllByCustomerCustomerId(Long customerId);

}
