package com.weble.linkedhouse.review.repository;

import com.weble.linkedhouse.review.entity.FeedbackHost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackHostRepository extends JpaRepository<FeedbackHost, Long> {

    Page<FeedbackHost> findAllByCustomerCustomerId(Long customerId, Pageable pageable);

}