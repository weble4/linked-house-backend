package com.weble.linkedhouse.notification.dtos;

import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import lombok.Getter;


@Getter

public class NotificationCreateRequest {
    private Long customerId;
    private NotificationType notificationType;
    private String notificationContent;

    private NotificationCreateRequest(Long customerId, NotificationType notificationType, String notificationContent) {
        this.customerId = customerId;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
    }

    public static NotificationCreateRequest of(Long customerId,
                                               NotificationType notificationType,
                                               String notificationContent) {
        return new NotificationCreateRequest(customerId, notificationType, notificationContent);
    }
}
