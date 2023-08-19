package com.weble.linkedhouse.report.dto;

import com.weble.linkedhouse.report.entity.Report;
import lombok.Getter;

@Getter
public class CustomerReviewReportRequest {

    private Long reporterId;
    private Long feedbackCustomerId;
    private String content;

    private CustomerReviewReportRequest(Long reporterId, Long feedbackCustomerId, String content) {
        this.reporterId = reporterId;
        this.feedbackCustomerId = feedbackCustomerId;
        this.content = content;
    }

    public static CustomerReviewReportRequest from(Report report) {
        return new CustomerReviewReportRequest(
                report.getReporter().getCustomerId(),
                report.getFeedbackCustomer().getFeedbackCustomerId(),
                report.getContent());
    }
}
