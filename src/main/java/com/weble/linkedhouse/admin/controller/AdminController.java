package com.weble.linkedhouse.admin.controller;

import com.weble.linkedhouse.admin.service.AdminService;
import com.weble.linkedhouse.admin.service.AdminFilter;
import com.weble.linkedhouse.notification.dtos.NoticeAll;
import com.weble.linkedhouse.review.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/notifications")
    public ResponseEntity<String> informNotification(@RequestBody NoticeAll request) {
        adminService.informNotification(request);
        return ResponseEntity.ok().body("메시지 전달이 성공했습니다.");
    }

    @GetMapping("/reviews/hosts/{customerId}")
    public Page<HostReviewResponse> findAllByHostReview(
            @PathVariable Long customerId,
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable) {
        return adminService.findAllByHostReview(customerId, pageable);
    }

    @GetMapping("/reviews/hosts/{feedbackHostId}")
    public ResponseEntity<HostReviewResponse> findByHostReviewId(@PathVariable Long feedbackHostId) {
        HostReviewResponse hostReviewResponse = adminService.findByHostReviewId(feedbackHostId);
        return ResponseEntity.ok().body(hostReviewResponse);
    }

    @DeleteMapping("/reviews/hosts/{feedbackHostId}")
    public void deleteHostReviewId(@PathVariable Long feedbackHostId) {
        adminService.deleteHostReviewId(feedbackHostId);
    }

    @GetMapping("/customers")
    public Page<Customer> findAllCustomers(
            @RequestParam(required = false) AdminFilter adminFilter,
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable) {
        return adminService.findAllCustomers(adminFilter, pageable);
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        return adminService.getCustomerById(customerId);
    }

    @PostMapping("/customers/suspend")
    public ResponseEntity<String> suspendUser(@RequestBody Long customerId) {
        adminService.suspendUser(customerId);
        return ResponseEntity.ok("유저 이용정지가 완료되었습니다.");
    }
    @GetMapping("/customers/{customerId}/suspended")
    public ResponseEntity<String> isCustomerSuspended(@PathVariable Long customerId) {
        boolean isSuspended = adminService.isCustomerSuspended(customerId);
        if (isSuspended) {
            return ResponseEntity.ok("유저는 정지 상태입니다.");
        } else {
            return ResponseEntity.ok("유저는 정지되지 않았습니다.");
        }
    }
}
