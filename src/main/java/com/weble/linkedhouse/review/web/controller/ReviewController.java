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
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<HostReviewResponse> createHostReview(@PathVariable Long customerId,
                                                               @RequestBody HostReviewRequest hostReviewRequest) {
        HostReviewResponse hostReviewResponse = feedbackHostService.createHostReview(hostReviewRequest);

        return ResponseEntity.ok().body(hostReviewResponse);
    }

    @GetMapping("/{feedbackHostId}")
    public ResponseEntity<HostReviewResponse> findByHostReviewId(@PathVariable Long feedbackHostId) {
        HostReviewResponse hostReviewResponse = feedbackHostService.findByHostReviewId(feedbackHostId);

        return ResponseEntity.ok().body(hostReviewResponse);
    }

    @GetMapping("/host/{customerId}")
    public List<HostReviewResponse> findAllByHostReview(@PathVariable Long customerId) {

        return feedbackHostService.findAllByHostReview(customerId);
    }

    @PostMapping("/{rentalId}")
    public ResponseEntity<CustomerReviewResponse> createCustomerReview(@PathVariable Long rentalId,
                                                                       @RequestBody CustomerReviewRequest customerReviewRequest) {
        CustomerReviewResponse customerReviewResponse = feedbackCustomerService.createCustomerReview(customerReviewRequest);

        return ResponseEntity.ok().body(customerReviewResponse);
    }

    @GetMapping("/{feedbackCustomerId}")
    public ResponseEntity<CustomerReviewResponse> findByCustomerId(@PathVariable Long feedbackCustomerId) {
        CustomerReviewResponse customerReviewResponse = feedbackCustomerService.findByCustomerReviewId(feedbackCustomerId);

        return ResponseEntity.ok().body(customerReviewResponse);
    }

    @GetMapping("/customer/{customerId}")
    public List<CustomerReviewResponse> findAllByCustomerReview(@PathVariable Long customerId) {

        return feedbackCustomerService.findAllByCustomerReview(customerId);
    }

    @PatchMapping("/{feedbackCustomerId}")
    public ResponseEntity<CustomerReviewResponse> updateCustomerReview(@PathVariable Long feedbackCustomerId,
                                                                       @RequestBody CustomerReviewRequest customerReviewRequest) {
        CustomerReviewResponse customerReviewResponse = feedbackCustomerService.updateCustomerReview(feedbackCustomerId, customerReviewRequest);

        return ResponseEntity.ok().body(customerReviewResponse);
    }

    @DeleteMapping("/{feedbackCustomerId}")
    public ResponseEntity<String> deleteCustomerReview(@PathVariable Long feedbackCustomerId) {
        feedbackCustomerService.deleteCustomerReview(feedbackCustomerId);
        return ResponseEntity.ok().body("삭제 되었습니다.");
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<List<CustomerReviewResponse>> findAllHouseReview(@PathVariable Long rentalId) {
        List<CustomerReviewResponse> allHouseReview = feedbackCustomerService.findAllHouseReview(rentalId);
        return ResponseEntity.ok().body(allHouseReview);
    }

    @DeleteMapping("/{feedbackHostId}")
    public ResponseEntity<String> deleteHostReview(@PathVariable Long feedbackHostId) {
        feedbackHostService.deleteHostReview(feedbackHostId);
        return ResponseEntity.ok().body("삭제 되었습니다.");
    }

    @PatchMapping("/{feedbackHostId}")
    public ResponseEntity<HostReviewResponse> updateHostReview(@PathVariable Long feedbackHostId,
                                                               @RequestBody HostReviewRequest hostReviewRequest){
        HostReviewResponse hostReviewResponse = feedbackHostService.updateHostReview(feedbackHostId, hostReviewRequest);
        return ResponseEntity.ok().body(hostReviewResponse);
    }

}