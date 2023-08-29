package com.weble.linkedhouse.report.entity;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.review.entity.FeedbackCustomer;
import com.weble.linkedhouse.review.entity.FeedbackHost;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "feedback_host_id")
    private FeedbackHost feedbackHost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_customer_id")
    private FeedbackCustomer feedbackCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter")
    private Customer reporter;

    @Column(name = "content")
    private String content;


    private Report(FeedbackHost feedbackHost, FeedbackCustomer feedbackCustomer, Customer reporter, String content) {
        this.feedbackHost = feedbackHost;
        this.feedbackCustomer = feedbackCustomer;
        this.reporter = reporter;
        this.content = content;
    }

    public static Report of(FeedbackCustomer feedbackCustomer,
                            Customer reporter, String content) {
        return new Report(null, feedbackCustomer, reporter, content);
    }

    public static Report of(FeedbackHost feedbackHost,
                            Customer reporter, String content) {
        return new Report(feedbackHost, null, reporter, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report that)) return false;
        return this.getReportId() != null && getReportId().equals(that.getReportId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReportId());
    }
}
