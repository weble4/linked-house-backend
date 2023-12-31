package com.weble.linkedhouse.notification.entity;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "createdAt")
})
public class Notification extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Column(name = "notification_type", nullable = false,
            columnDefinition = "VARCHAR(50) DEFAULT 'alert'")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "notification_content", nullable = false)
    private String notificationContent;

    @Builder
    private Notification(Customer customer, String notificationContent) {
        this.customer = customer;
        this.notificationType = NotificationType.ALERT;
        this.notificationContent = notificationContent;
    }

    public static Notification of(Customer customer, String notificationContent){
        return new Notification(customer, notificationContent);
    }

    public void updateCheck() {
        this.notificationType = NotificationType.CHECK;
    }
}
