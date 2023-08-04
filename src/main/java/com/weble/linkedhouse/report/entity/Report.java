package com.weble.linkedhouse.report.entity;

import com.weble.linkedhouse.review.entity.FeedbackCustomer;
import com.weble.linkedhouse.review.entity.FeedbackHost;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "feedback_host_id")
    private FeedbackHost feedbackHost;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "feedback_customer_id")
    private FeedbackCustomer feedbackCustomer;

    @Column(name = "reported_customer")
    private String reportedCustomer;

    @Column(name = "reporter")
    private String reporter;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "content")
    private String content;


    public Report(FeedbackHost feedbackHost, FeedbackCustomer feedbackCustomer,
                  String reportedCustomer, String reporter,
                  String reportType, String content) {
        this.feedbackHost = feedbackHost;
        this.feedbackCustomer = feedbackCustomer;
        this.reportedCustomer = reportedCustomer;
        this.reporter = reporter;
        this.reportType = reportType;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report report)) return false;
        return Objects.equals(getReportId(), report.getReportId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReportId());
    }
}
