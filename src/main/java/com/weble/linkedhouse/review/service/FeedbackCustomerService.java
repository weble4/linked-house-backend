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

    public Long createCustomerReview(CustomerReviewRequest request) {
        FeedbackCustomer entity = feedbackCustomerRepository.save(request.toEntity());
        return entity.getFeedbackcustomerId();
    }

    public void deleteCustomerReview(Long feedbackCustomerId) {
        FeedbackCustomer feedbackCustomer = feedbackCustomerRepository.findById(feedbackCustomerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + feedbackCustomerId));
        feedbackCustomerRepository.delete(feedbackCustomer);
    }

    public List<CustomerReviewResponse> findAllCustomerReview(Long customerId) {
        return feedbackCustomerRepository.findByCustomerCustomerId(customerId)
                .map(feedbackCustomers ->
                        feedbackCustomers.stream()
                                .map(CustomerReviewResponse::from)
                                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public CustomerReviewResponse updateCustomerReview(Long feedbackCustomerId, CustomerReviewRequest request) {
        FeedbackCustomer feedbackCustomer = feedbackCustomerRepository.findById(feedbackCustomerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + feedbackCustomerId));

        feedbackCustomer.updateCustomerReview(request);
        return new CustomerReviewResponse();
    }

    public CustomerReviewResponse findByCustomerId(Long feedbackCustomerId) {
        return feedbackCustomerRepository.findById(feedbackCustomerId).map(CustomerReviewResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + feedbackCustomerId));
    }

    public List<CustomerReviewResponse> findAllHouseReview(Long rentalId) {
        return feedbackCustomerRepository.findAllByHouseRentalId(rentalId)
                .map(feedbackCustomers ->
                        feedbackCustomers.stream()
                                .map(CustomerReviewResponse::from)
                                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}

