package com.weble.linkedhouse.notification.dtos;

import lombok.Getter;


@Getter

public class NotificationCreateRequest {
    private Long customerId;
    private String notificationContent;

    private NotificationCreateRequest(Long customerId, String notificationContent) {
        this.customerId = customerId;
        this.notificationContent = notificationContent;
    }

    public static NotificationCreateRequest of(Long customerId,
                                               String notificationContent) {
        return new NotificationCreateRequest(customerId, notificationContent);
    }
}
