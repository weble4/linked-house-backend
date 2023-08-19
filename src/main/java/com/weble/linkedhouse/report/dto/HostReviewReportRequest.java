package com.weble.linkedhouse.report.dto;

import com.weble.linkedhouse.report.entity.Report;
import lombok.Getter;

@Getter
public class HostReviewReportRequest {

    private Long reporterId;
    private Long feedbackHostId;
    private String content;

    private HostReviewReportRequest(Long reporterId, Long feedbackHostId, String content) {
        this.reporterId = reporterId;
        this.feedbackHostId = feedbackHostId;
        this.content = content;
    }

    public static HostReviewReportRequest from(Report report) {
        return new HostReviewReportRequest(
                report.getReporter().getCustomerId(),
                report.getFeedbackHost().getFeedbackHostId(),
                report.getContent());
    }
}
