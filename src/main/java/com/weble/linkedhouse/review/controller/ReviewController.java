package com.weble.linkedhouse.review.controller;

import com.weble.linkedhouse.review.dtos.response.CustomerReviewResponse;
import com.weble.linkedhouse.review.dtos.response.CustomerFeedbackResponse;
import com.weble.linkedhouse.review.dtos.response.HostFeedbackResponse;
import com.weble.linkedhouse.review.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.review.service.FeedbackCustomerService;
import com.weble.linkedhouse.review.service.FeedbackHostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final FeedbackCustomerService feedbackCustomerService;
    private final FeedbackHostService feedbackHostService;

    @PostMapping("/{customerId}")
    public ResponseEntity<HostReviewResponse> createHostReview(@PathVariable Long customerId) {
        HostReviewResponse hostReviewResponse = feedbackHostService.createHostReview(customerId);

        return ResponseEntity.ok().body(hostReviewResponse);
    }

    @PostMapping("/{rentalId}")
    public ResponseEntity<CustomerReviewResponse> createCustomerReview(@PathVariable Long rentalId) {
        CustomerReviewResponse customerReviewResponse = feedbackCustomerService.createCustomerReview(rentalId);

        return CustomerReviewResponse.ok().body(customerReviewResponse);
    }

    @DeleteMapping("/{feedbackCustomerId}")
    public void deleteCustomerReview(@PathVariable Long feedbackCustomerId) {

        feedbackCustomerService.deleteCustomerReview(feedbackCustomerId);
    }

    @PutMapping("/{feedbackCustomerId}")
    public ResponseEntity<CustomerFeedbackResponse> updateCustomerReview(@PathVariable Long feedbackCustomerId) {
        CustomerFeedbackResponse customerFeedbackResponse = feedbackCustomerService.updateCustomerReview(feedbackCustomerId);

        return ResponseEntity.ok().body(customerFeedbackResponse);
    }

    @GetMapping("/{feedbackCustomerId}")
    public ResponseEntity<CustomerFeedbackResponse> findByCustomerId(@PathVariable Long feedbackCustomerId) {
        CustomerFeedbackResponse customerFeedbackResponse = feedbackCustomerService.findByCustomerId(feedbackCustomerId);

        return ResponseEntity.ok().body(customerFeedbackResponse);
    }

    @GetMapping("/{feedbackHostId}")
    public ResponseEntity<HostFeedbackResponse> findReviewByHostId(@PathVariable Long feedbackHostId) {
        HostFeedbackResponse hostfeedbackResponse = feedbackHostService.findReviewByHostId(feedbackHostId);

        return ResponseEntity.ok().body(hostfeedbackResponse);
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<CustomerFeedbackResponse> findAllHouseReview(@PathVariable Long rentalId) {
        CustomerFeedbackResponse customerFeedbackResponse = feedbackCustomerService.findAllHouseReview(rentalId);

        return ResponseEntity.ok().body(customerFeedbackResponse);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerReviewResponse> findAllCustomerReview(@PathVariable Long customerId) {
        CustomerReviewResponse customerReviewResponse = feedbackCustomerService.findAllCustomerReview(customerId);

        return ResponseEntity.ok().body(customerReviewResponse);
    }





    /*
    @GetMapping("/{feedback_customer_id")
    public ResponseEntity<FeedbackResponse> getUser(@PathVariable Long customerId) {
        HouseResponse houseResponse = feedbackCustomerService.getHouse(customerId);

        return HouseResponse.ok().body(Response);
    }

     */

}






