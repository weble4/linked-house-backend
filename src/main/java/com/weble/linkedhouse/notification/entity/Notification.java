package com.weble.linkedhouse.notification.entity;

import com.weble.linkedhouse.notification.entity.constant.NotificationContent;
import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "notification_type", nullable = false,
            columnDefinition = "VARCHAR(50) DEFAULT 'CHECK'")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "notification_content", nullable = false,
            columnDefinition = "VARCHAR(50) DEFAULT 'CHECK'")
    @Enumerated(EnumType.STRING)
    private NotificationContent notificationContent;

    private Notification(Long customerId, NotificationType notificationType, NotificationContent notificationContent) {
        this.customerId = customerId;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
    }

    public static Notification of(Long customerId, NotificationType notificationType, NotificationContent notificationContent){
        return new Notification(customerId, notificationType, notificationContent);
    }

}

