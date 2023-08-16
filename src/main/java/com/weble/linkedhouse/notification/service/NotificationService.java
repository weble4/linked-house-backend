package com.weble.linkedhouse.notification.service;


import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.NotFoundNotification;
import com.weble.linkedhouse.notification.dtos.NotificationCreateRequest;
import com.weble.linkedhouse.notification.dtos.NotificationDto;
import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.repository.NotificationRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final CustomerRepository customerRepository;

    public Page<NotificationDto> getAllNotificationDtos(UserDetailsImpl userDetails, Pageable pageable) {
        return notificationRepository.findAllByCustomerCustomerEmail(userDetails.getUsername(), pageable)
                .map(NotificationDto::fromEntity);
    }

    public NotificationDto getSingleNotification(Long notificationId) {
        return notificationRepository.findById(notificationId).map(NotificationDto::fromEntity)
                .orElseThrow(NotFoundNotification::new);
    }

    public NotificationDto createNotificationDto(NotificationCreateRequest requestDto) {
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(NotExistCustomer::new);

        Notification notification = Notification.of(
                customer,
                requestDto.getNotificationType(),
                requestDto.getNotificationContent()
        );

        Notification savedNotification = notificationRepository.save(notification);

        return NotificationDto.fromEntity(savedNotification);
    }
}