package com.weble.linkedhouse.review.web.controller;

import com.weble.linkedhouse.review.web.dtos.request.CustomerReviewRequest;
import com.weble.linkedhouse.review.web.dtos.request.HostReviewRequest;
import com.weble.linkedhouse.review.web.dtos.response.CustomerReviewResponse;
import com.weble.linkedhouse.review.web.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.review.service.FeedbackCustomerService;
import com.weble.linkedhouse.review.service.FeedbackHostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final FeedbackCustomerService feedbackCustomerService;
    private final FeedbackHostService feedbackHostService;

    @PostMapping("/{customerId}")
    public ResponseEntity<HostReviewResponse> createHostReview(@PathVariable Long customerId, @RequestBody HostReviewRequest hostReviewRequest) {
        HostReviewResponse hostReviewResponse = feedbackHostService.createHostReview(hostReviewRequest);

        return ResponseEntity.ok().body(hostReviewResponse);
    }

    @GetMapping("/{customerId}")
    public List<HostReviewResponse> getAllHostReview(@PathVariable Long customerId) {
        return feedbackHostService.getAllHostReview(customerId);
    }

    @PostMapping("/{rentalId}")
    public ResponseEntity<CustomerReviewResponse> createCustomerReview(@PathVariable Long rentalId) {
        CustomerReviewResponse customerReviewResponse = feedbackCustomerService.createCustomerReview(rentalId);

        return ResponseEntity.ok().body(customerReviewResponse);
    }

    @DeleteMapping("/{feedbackCustomerId}")
    public void deleteCustomerReview(@PathVariable Long feedbackCustomerId) {

        feedbackCustomerService.deleteCustomerReview(feedbackCustomerId);
    }

    @PutMapping("/{feedbackCustomerId}")
    public ResponseEntity<CustomerReviewResponse> updateCustomerReview(@PathVariable Long feedbackCustomerId,
                                                                         @RequestBody CustomerReviewRequest request) {
        CustomerReviewResponse customerFeedbackResponse =
                feedbackCustomerService.updateCustomerReview(feedbackCustomerId, request);

        return ResponseEntity.ok().body(customerFeedbackResponse);
    }

    @GetMapping("/{feedbackCustomerId}")
    public ResponseEntity<CustomerReviewResponse> findByCustomerId(@PathVariable Long feedbackCustomerId) {
        CustomerReviewResponse customerReviewResponse =
                feedbackCustomerService.findByCustomerId(feedbackCustomerId);

        return ResponseEntity.ok().body(customerFeedbackResponse);
    }

    @GetMapping("/{feedbackHostId}")
    public ResponseEntity<HostFeedbackResponse> findReviewByHostId(@PathVariable Long feedbackHostId) {
        HostFeedbackResponse hostfeedbackResponse = feedbackHostService.findReviewByHostId(feedbackHostId);

        return ResponseEntity.ok().body(hostfeedbackResponse);
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<CustomerReviewResponse> findAllHouseReview(@PathVariable Long rentalId) {
        CustomerReviewResponse customerReviewResponse = feedbackCustomerService.findAllHouseReview(rentalId);

        return ResponseEntity.ok().body(customerReviewResponse);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerReviewResponse> findAllCustomerReview(@PathVariable Long customerId) {
        CustomerReviewResponse customerReviewResponse = feedbackCustomerService.findAllCustomerReview(customerId);

        return ResponseEntity.ok().body(customerReviewResponse);
    }


}






