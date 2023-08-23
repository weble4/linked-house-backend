package com.weble.linkedhouse.admin.controller;

import com.weble.linkedhouse.admin.service.AdminService;
import com.weble.linkedhouse.notification.dtos.NoticeAll;
import com.weble.linkedhouse.review.dtos.response.HostReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/notifications")
    public ResponseEntity<String> informNotification(@RequestBody NoticeAll request){
        adminService.informNotification(request);
        return ResponseEntity.ok().body("메시지 전달이 성공했습니다.");
    }

    @GetMapping("/reviews/hosts/{customerId}")
    public Page<HostReviewResponse> findAllByHostReview(@PathVariable Long customerId,
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

}
