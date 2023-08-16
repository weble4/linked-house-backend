package com.weble.linkedhouse.review.domain.repository;

import com.weble.linkedhouse.review.domain.entity.FeedbackHost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackHostRepository extends JpaRepository<FeedbackHost, Long> {

    List<FeedbackHost> findAllByCustomerCustomerId(Long customerId);
}