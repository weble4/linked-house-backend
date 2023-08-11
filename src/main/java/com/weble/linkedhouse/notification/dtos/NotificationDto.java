package com.weble.linkedhouse.notification.dtos;

import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long notificationId;
    private Long customerId;
    private NotificationType notificationType;
    private String notificationContent;

    public static NotificationDto fromEntity(Notification notification) {
        return new NotificationDto(
                notification.getNotificationId(),
                notification.getCustomer().getCustomerId(),
                notification.getNotificationType(),
                notification.getNotificationContent()
        );
    }
}