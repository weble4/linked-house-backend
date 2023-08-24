package com.weble.linkedhouse.review.service;


import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistReview;
import com.weble.linkedhouse.review.dtos.request.HostReviewRequest;
import com.weble.linkedhouse.review.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.review.entity.FeedbackHost;
import com.weble.linkedhouse.review.repository.FeedbackHostRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CustomerRepository customerRepository;

    @Transactional
    public HostReviewResponse createHostReview(Long customerId,
                                               UserDetailsImpl userDetails,
                                               HostReviewRequest request) {

        Customer customer = customerRepository.getReferenceById(customerId);
        Customer writer = customerRepository.getReferenceById(userDetails.getUserId());

        FeedbackHost review = FeedbackHost.of(
                customer,
                writer,
                request.getTitle(),
                request.getContent(),
                request.getAttitude(),
                request.getDamageDegree()
        );

        FeedbackHost saveReview = feedbackHostRepository.save(review);
        return HostReviewResponse.from(saveReview);
    }

    public HostReviewResponse findByHostReviewId(Long feedbackHostId) {
        return feedbackHostRepository.findById(feedbackHostId).map(HostReviewResponse::from)
                .orElseThrow(NotExistReview::new);
    }

    public Page<HostReviewResponse> findAllByHostReview(Long customerId, Pageable pageable) {
        return feedbackHostRepository.findAllByCustomerCustomerId(customerId, pageable)
                .map(HostReviewResponse::from);
    }

    @Transactional
    public void deleteHostReview(Long feedbackHostId) {
        feedbackHostRepository.deleteById(feedbackHostId);
    }

    @Transactional
    public HostReviewResponse updateHostReview(Long feedbackHostId, HostReviewRequest request) {
        FeedbackHost feedbackHost = feedbackHostRepository.findById(feedbackHostId)
                .orElseThrow(NotExistReview::new);

        feedbackHost.updateReview(
                request.getContent(),
                request.getAttitude(),
                request.getDamageDegree());

        return HostReviewResponse.from(feedbackHost);
    }
}