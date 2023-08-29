package com.weble.linkedhouse.report.dto;

import com.weble.linkedhouse.report.entity.Report;
import lombok.Getter;

@Getter
public class CustomerReviewReportRequest {

    private Long feedbackCustomerId;
    private String content;

    private CustomerReviewReportRequest(Long feedbackCustomerId, String content) {
        this.feedbackCustomerId = feedbackCustomerId;
        this.content = content;
    }

    public static CustomerReviewReportRequest from(Report report) {
        return new CustomerReviewReportRequest(
                report.getFeedbackCustomer().getFeedbackCustomerId(),
                report.getContent());
    }
}
