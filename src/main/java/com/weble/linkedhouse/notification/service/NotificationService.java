package com.weble.linkedhouse.notification.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import com.weble.linkedhouse.notification.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CustomerRepository customerRepository; // Import CustomerRepository

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, CustomerRepository customerRepository) {
        this.notificationRepository = notificationRepository;
        this.customerRepository = customerRepository;
    }

    public List<Notification> getAllNotification() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

    public Notification createNotification(Long customerId, NotificationType notificationType, String notificationContent) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));

        Notification notification = Notification.of(customer, notificationType, notificationContent);
        return notificationRepository.save(notification);
    }
}