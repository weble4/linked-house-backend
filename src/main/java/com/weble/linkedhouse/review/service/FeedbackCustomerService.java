package com.weble.linkedhouse.review.service;

import com.weble.linkedhouse.review.domain.entity.FeedbackCustomer;
import com.weble.linkedhouse.review.domain.repository.FeedbackCustomerRepository;
import com.weble.linkedhouse.review.web.dtos.request.CustomerReviewRequest;
import com.weble.linkedhouse.review.web.dtos.response.CustomerReviewResponse;
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
public class FeedbackCustomerService {

    private final FeedbackCustomerRepository feedbackCustomerRepository;

    @Transactional
    public CustomerReviewResponse createCustomerReview(CustomerReviewRequest request) {
        FeedbackCustomer review = feedbackCustomerRepository.save(request.toEntity());

        return CustomerReviewResponse.from(review);
    }

    public CustomerReviewResponse findByCustomerReviewId(Long feedbackCustomerId) {
        FeedbackCustomer check = feedbackCustomerRepository.findById(feedbackCustomerId)
                .orElseThrow();

        return CustomerReviewResponse.from(check);
    }

    public List<CustomerReviewResponse> findAllByCustomerReview(Long customerId) {
        return feedbackCustomerRepository.findAllByCustomerCustomerId(customerId)
                .stream().map(CustomerReviewResponse::from).collect(Collectors.toList());
    }

    public void deleteCustomerReview(Long feedbackCustomerId) {
        feedbackCustomerRepository.deleteById(feedbackCustomerId);
    }

    @Transactional
    public CustomerReviewResponse updateCustomerReview(Long feedbackCustomerId, CustomerReviewRequest request) {
        FeedbackCustomer feedbackCustomer = feedbackCustomerRepository.findById(feedbackCustomerId).orElseThrow();

        feedbackCustomer.updateReview(request.getContent(), request.getScoreClean(),
                request.getScoreCommunication(), request.getScoreSatisfaction());
        return CustomerReviewResponse.from(feedbackCustomer);
    }

    public List<CustomerReviewResponse> findAllHouseReview(Long rentalId) {
        return feedbackCustomerRepository.findAllByHouseRentalId(rentalId)
                .stream().map(CustomerReviewResponse::from).collect(Collectors.toList());

    }
}