package com.weble.linkedhouse.report.controller;

import com.weble.linkedhouse.report.dto.CustomerReviewReportRequest;
import com.weble.linkedhouse.report.dto.HostReviewReportRequest;
import com.weble.linkedhouse.report.service.ReportService;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000" )
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/customers/{feedbackCustomerId}")
    public ResponseEntity<String> customerReviewReport(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @RequestBody CustomerReviewReportRequest reportRequest) {

        reportService.customerReviewReport(userDetails, reportRequest);
        return ResponseEntity.ok("신고되었습니다.");
    }

    @PostMapping("/hosts/{feedbackHostId}")
    public ResponseEntity<String> hostReviewReport(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @RequestBody HostReviewReportRequest reportRequest) {

        reportService.hostReviewReport(userDetails, reportRequest);
        return ResponseEntity.ok("신고되었습니다.");
    }
}
