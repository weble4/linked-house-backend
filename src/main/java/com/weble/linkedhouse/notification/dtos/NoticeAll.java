package com.weble.linkedhouse.notification.dtos;

import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import lombok.Getter;

import java.util.List;


@Getter
public class NoticeAll {
    private List<Long> customerId;
    private NotificationType notificationType;
    private String notificationContent;

    private NoticeAll(List<Long> customerId, NotificationType notificationType, String notificationContent) {
        this.customerId = customerId;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
    }

    public static NoticeAll of(List<Long> customerId,
                               NotificationType notificationType,
                               String notificationContent) {
        return new NoticeAll(customerId, notificationType, notificationContent);
    }

    
}
