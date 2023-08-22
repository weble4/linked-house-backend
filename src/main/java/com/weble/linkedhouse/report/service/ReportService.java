package com.weble.linkedhouse.report.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.NotReportReview;
import com.weble.linkedhouse.report.dto.CustomerReviewReportRequest;
import com.weble.linkedhouse.report.dto.HostReviewReportRequest;
import com.weble.linkedhouse.report.entity.Report;
import com.weble.linkedhouse.report.repository.ReportRepository;
import com.weble.linkedhouse.review.entity.FeedbackCustomer;
import com.weble.linkedhouse.review.entity.FeedbackHost;
import com.weble.linkedhouse.review.repository.FeedbackCustomerRepository;
import com.weble.linkedhouse.review.repository.FeedbackHostRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final FeedbackCustomerRepository feedbackCustomerRepository;
    private final FeedbackHostRepository feedbackHostRepository;
    private final CustomerRepository customerRepository;

    public void hostReviewReport(UserDetailsImpl userDetails, HostReviewReportRequest reportRequest) {
        FeedbackHost feedbackHost = feedbackHostRepository.findById(reportRequest.getFeedbackHostId())
                .orElseThrow(EntityNotFoundException::new);

        Customer customer = customerRepository.findById(userDetails.getUserId())
                .orElseThrow(NotExistCustomer::new);

        if (!feedbackHost.getCustomer().getCustomerId().equals(userDetails.getUserId())) {
            throw new NotReportReview();
        }

        reportRepository.save(Report.of(
                feedbackHost,
                customer,
                reportRequest.getContent()));
    }

    public void customerReviewReport(UserDetailsImpl userDetails, CustomerReviewReportRequest reportRequest) {
        FeedbackCustomer feedbackCustomer = feedbackCustomerRepository.findById(reportRequest.getFeedbackCustomerId())
                .orElseThrow(EntityNotFoundException::new);

        Customer customer = customerRepository.findById(userDetails.getUserId())
                .orElseThrow(NotExistCustomer::new);

        reportRepository.save(Report.of(
                feedbackCustomer,
                customer,
                reportRequest.getContent()));
    }
}
