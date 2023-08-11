package com.weble.linkedhouse.notification.dtos;

import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import lombok.Getter;


@Getter

public class NotificationCreateRequestDto {
    private Long customerId;
    private NotificationType notificationType;
    private String notificationContent;

    public NotificationCreateRequestDto(Long customerId, NotificationType notificationType, String notificationContent) {
        this.customerId = customerId;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
    }
}
