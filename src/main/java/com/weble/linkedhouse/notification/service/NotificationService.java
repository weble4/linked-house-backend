package com.weble.linkedhouse.notification.service;


import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.notification.dtos.NotificationCreateRequestDto;
import com.weble.linkedhouse.notification.dtos.NotificationDto;
import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import com.weble.linkedhouse.notification.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final CustomerRepository customerRepository;

    public List<NotificationDto> getAllNotificationDtos() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(NotificationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public NotificationDto getNotificationDtoById(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElse(null);

        if (notification != null) {
            return NotificationDto.fromEntity(notification);
        }
        return null;
    }

    public NotificationDto createNotificationDto(NotificationCreateRequestDto requestDto) {
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + requestDto.getCustomerId()));

        Notification notification = Notification.of(
                customer,
                requestDto.getNotificationType(),
                requestDto.getNotificationContent()
        );

        Notification savedNotification = notificationRepository.save(notification);

        return NotificationDto.fromEntity(savedNotification);
    }
}