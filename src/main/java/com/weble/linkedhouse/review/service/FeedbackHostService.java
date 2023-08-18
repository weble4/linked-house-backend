package com.weble.linkedhouse.review.service;


import com.weble.linkedhouse.review.dtos.request.HostReviewRequest;
import com.weble.linkedhouse.review.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.review.entity.FeedbackHost;
import com.weble.linkedhouse.review.repository.FeedbackHostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackHostService {

    private final FeedbackHostRepository feedbackHostRepository;

    @Transactional
    public HostReviewResponse createHostReview(HostReviewRequest request) {
        FeedbackHost review = feedbackHostRepository.save(request.toEntity());

        return HostReviewResponse.from(review);
    }

    public HostReviewResponse findByHostReviewId(Long feedbackHostId) {
        FeedbackHost check = feedbackHostRepository.findById(feedbackHostId)
                .orElseThrow();

        return HostReviewResponse.from(check);
    }

    public List<HostReviewResponse> findAllByHostReview(Long customerId) {

        return feedbackHostRepository.findAllByCustomerCustomerId(customerId).stream()
                .map(HostReviewResponse::from).collect(Collectors.toList());
    }

    public void deleteHostReview(Long feedbackHostId) {
        feedbackHostRepository.deleteById(feedbackHostId);
    }

    public HostReviewResponse updateHostReview(Long feedbackHostId, HostReviewRequest request) {
        FeedbackHost feedbackHost = feedbackHostRepository.findById(feedbackHostId).orElseThrow();
        feedbackHost.updateReview(request.getContent(), request.getAttitude(), request.getDamageDegree());

        return HostReviewResponse.from(feedbackHost);
    }
}