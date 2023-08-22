package com.weble.linkedhouse.review.controller;

import com.weble.linkedhouse.review.dtos.request.CustomerReviewRequest;
import com.weble.linkedhouse.review.dtos.request.HostReviewRequest;
import com.weble.linkedhouse.review.dtos.response.CustomerReviewResponse;
import com.weble.linkedhouse.review.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.review.service.FeedbackCustomerService;
import com.weble.linkedhouse.review.service.FeedbackHostService;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final FeedbackCustomerService feedbackCustomerService;
    private final FeedbackHostService feedbackHostService;

    @PostMapping("/hosts/{customerId}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<HostReviewResponse> createHostReview(@PathVariable Long customerId,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                               @RequestBody HostReviewRequest hostReviewRequest) {
        HostReviewResponse hostReviewResponse =
                feedbackHostService.createHostReview(customerId, userDetails, hostReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(hostReviewResponse);
    }

    @PostMapping("/customers/{rentalId}")
    public ResponseEntity<CustomerReviewResponse> createCustomerReview(@PathVariable Long rentalId,
                                                                       @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                       @RequestBody CustomerReviewRequest customerReviewRequest) {
        CustomerReviewResponse customerReviewResponse =
                feedbackCustomerService.createCustomerReview(rentalId, userDetails, customerReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerReviewResponse);
    }

    // 호스트 리뷰 하나 단독 조회
    @GetMapping("/hosts/{feedbackHostId}")
    public ResponseEntity<HostReviewResponse> findByHostReviewId(@PathVariable Long feedbackHostId) {
        HostReviewResponse hostReviewResponse = feedbackHostService.findByHostReviewId(feedbackHostId);
        return ResponseEntity.ok().body(hostReviewResponse);
    }

    //고객 리뷰 하나 단독조회
    @GetMapping("/customers/{feedbackCustomerId}")
    public ResponseEntity<CustomerReviewResponse> findByCustomerId(@PathVariable Long feedbackCustomerId) {
        CustomerReviewResponse customerReviewResponse =
                feedbackCustomerService.findByCustomerReviewId(feedbackCustomerId);
        return ResponseEntity.ok().body(customerReviewResponse);
    }

    @GetMapping("/all-hosts/{customerId}")
    public Page<HostReviewResponse> findAllByHostReview(@PathVariable Long customerId,
                                                        @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable) {
        return feedbackHostService.findAllByHostReview(customerId, pageable);
    }

    @GetMapping("/all-customers/{customerId}")
    public Page<CustomerReviewResponse> findAllByCustomerReview(@PathVariable Long customerId,
                                                                @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable) {
        return feedbackCustomerService.findAllByCustomerReview(customerId, pageable);
    }

    @GetMapping("/houses/{rentalId}")
    public Page<CustomerReviewResponse> findAllHouseReview(@PathVariable Long rentalId,
                                                           @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable) {
        return feedbackCustomerService.findAllHouseReview(rentalId, pageable);
    }


    @PatchMapping("/customers/{feedbackCustomerId}")
    public ResponseEntity<CustomerReviewResponse> updateCustomerReview(@PathVariable Long feedbackCustomerId,
                                                                       @RequestBody CustomerReviewRequest customerReviewRequest) {
        CustomerReviewResponse customerReviewResponse = feedbackCustomerService
                                    .updateCustomerReview(feedbackCustomerId, customerReviewRequest);

        return ResponseEntity.ok().body(customerReviewResponse);
    }

    @PatchMapping("/hosts/{feedbackHostId}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<HostReviewResponse> updateHostReview(@PathVariable Long feedbackHostId,
                                                               @RequestBody HostReviewRequest hostReviewRequest){
        HostReviewResponse hostReviewResponse = feedbackHostService.updateHostReview(feedbackHostId, hostReviewRequest);
        return ResponseEntity.ok().body(hostReviewResponse);
    }

    @DeleteMapping("/customers/{feedbackCustomerId}")
    public ResponseEntity<String> deleteCustomerReview(@PathVariable Long feedbackCustomerId) {
        feedbackCustomerService.deleteCustomerReview(feedbackCustomerId);
        return ResponseEntity.ok().body("삭제 되었습니다.");
    }

    @DeleteMapping("/hosts/{feedbackHostId}")
    public ResponseEntity<String> deleteHostReview(@PathVariable Long feedbackHostId) {
        feedbackHostService.deleteHostReview(feedbackHostId);
        return ResponseEntity.ok().body("삭제 되었습니다.");
    }
}
