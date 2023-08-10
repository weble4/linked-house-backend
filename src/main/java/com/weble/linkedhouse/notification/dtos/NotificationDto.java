package com.weble.linkedhouse.notification.dtos;

import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import lombok.Data;

@Data
public class NotificationDto {
    private Long notificationId;
    private Long customerId;
    private NotificationType notificationType;
    private String notificationContent;

    public static NotificationDto fromEntity(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setNotificationId(notification.getNotificationId());
        dto.setCustomerId(notification.getCustomer().getId());
        dto.setNotificationType(notification.getNotificationType());
        dto.setNotificationContent(notification.getNotificationContent());
        return dto;
    }
}
