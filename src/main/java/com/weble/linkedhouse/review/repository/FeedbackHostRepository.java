package com.weble.linkedhouse.review.repository;

import com.weble.linkedhouse.review.entity.FeedbackHost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackHostRepository extends JpaRepository<FeedbackHost, Long> {

    Optional<FeedbackHost> findByHostId(String HostId);

}
