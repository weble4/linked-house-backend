package com.weble.linkedhouse.review.service;

import com.weble.linkedhouse.review.web.dtos.request.HostReviewRequest;
import com.weble.linkedhouse.review.web.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.review.domain.entity.FeedbackHost;
import com.weble.linkedhouse.review.domain.repository.FeedbackCustomerRepository;
import com.weble.linkedhouse.review.domain.repository.FeedbackHostRepository;
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

    private final FeedbackCustomerRepository feedbackCustomerRepository;
    private final FeedbackHostRepository feedbackHostRepository;

    public List<HostReviewResponse> getAllHostReview(Long customerId) {
        return feedbackHostRepository.findAllByCustomerCustomerId(customerId)
                .map(feedbackHosts -> feedbackHosts.stream()
                        .map(HostReviewResponse::from).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Transactional
    public HostReviewResponse createHostReview(HostReviewRequest hostReviewRequest) {
        FeedbackHost review = feedbackHostRepository.save(hostReviewRequest.toEntity());
        return HostReviewResponse.from(review);
    }

    @Transactional
    public HostFeedbackResponse findReviewByHostId(Long feedbackHostId) {
        FeedbackHost feedbackHost = FeedbackHostRepository.findById(feedbackHostId)
    }
}
