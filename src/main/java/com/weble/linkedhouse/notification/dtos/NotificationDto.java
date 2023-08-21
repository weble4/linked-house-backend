package com.weble.linkedhouse.notification.dtos;

import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationDto {

    private Long notificationId;
    private Long customerId;
    private NotificationType notificationType;
    private String notificationContent;
    private LocalDateTime createdAt;

    private NotificationDto(Long notificationId, Long customerId, NotificationType notificationType,
                           String notificationContent, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.customerId = customerId;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
        this.createdAt = createdAt;
    }

    public static NotificationDto fromEntity(Notification notification) {
        return new NotificationDto(
                notification.getNotificationId(),
                notification.getCustomer().getCustomerId(),
                notification.getNotificationType(),
                notification.getNotificationContent(),
                notification.getCreatedAt()
        );
    }

}