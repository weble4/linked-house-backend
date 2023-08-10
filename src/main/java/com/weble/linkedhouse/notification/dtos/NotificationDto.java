package com.weble.linkedhouse.notification.dtos;

import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import lombok.Data;

@Data
public class NotificationDto {
    private Long notificationId;
    private Long customerId;
    private NotificationType notificationType;
    private String notificationContent;
}
