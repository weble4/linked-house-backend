package com.weble.linkedhouse.admin.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.NotExistReview;
import com.weble.linkedhouse.notification.dtos.NoticeAll;
import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.repository.NotificationRepository;
import com.weble.linkedhouse.review.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.review.repository.FeedbackHostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final NotificationRepository notificationRepository;
    private final CustomerRepository customerRepository;
    private final FeedbackHostRepository feedbackHostRepository;

    @Transactional
    public void informNotification(NoticeAll request) {

        List<Notification> notificationList = new ArrayList<>();
        for (int i = 0; i < request.getCustomerId().size(); i++) {
            Customer customer = customerRepository.findById(request.getCustomerId().get(i))
                    .orElseThrow(NotExistCustomer::new);

            Notification notification = Notification.of(customer, request.getNotificationContent());
            notificationList.add(notification);
        }
        notificationRepository.saveAll(notificationList);
    }

    public Page<HostReviewResponse> findAllByHostReview(Long customerId, Pageable pageable) {
        return feedbackHostRepository.findAllByCustomerCustomerId(customerId, pageable)
                .map(HostReviewResponse::from);
    }

    public HostReviewResponse findByHostReviewId(Long feedbackHostId) {
        return feedbackHostRepository.findById(feedbackHostId)
                .map(HostReviewResponse::from)
                .orElseThrow(NotExistReview::new);
    }

    @Transactional
    public void deleteHostReviewId(Long feedbackHostId) {
        feedbackHostRepository.deleteById(feedbackHostId);
    }

}
