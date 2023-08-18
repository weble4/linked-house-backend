package com.weble.linkedhouse.admin.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.notification.dtos.NoticeAll;
import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final NotificationRepository notificationRepository;
    private final CustomerRepository customerRepository;

    public void informNotification(NoticeAll request) {

        for (int i = 0; i < request.getCustomerId().size(); i++) {
            Customer customer = customerRepository.findById(request.getCustomerId().get(i))
                    .orElseThrow(NotExistCustomer::new);

            Notification save = notificationRepository.save(Notification.of(
                    customer,
                    request.getNotificationType(),
                    request.getNotificationContent()));
        }
    }

}
