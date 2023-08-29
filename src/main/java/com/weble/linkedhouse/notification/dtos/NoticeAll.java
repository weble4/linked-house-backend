package com.weble.linkedhouse.notification.dtos;

import lombok.Getter;

import java.util.List;


@Getter
public class NoticeAll {
    private List<Long> customerId;
    private String notificationContent;

    private NoticeAll(List<Long> customerId, String notificationContent) {
        this.customerId = customerId;
        this.notificationContent = notificationContent;
    }

    public static NoticeAll of(List<Long> customerId,
                               String notificationContent) {
        return new NoticeAll(customerId, notificationContent);
    }

}
