package com.weble.linkedhouse.notification.service;

import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.entity.constant.NotificationType;
import com.weble.linkedhouse.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    public Notification createNotification(Long customerId, NotificationType notificationType, String notificationContent){
      // Customer customer = //customerID entity 로부터 가져와야함?

      Notification notification = Notification.of(customer, notificationType, notificationContent);
      return notificationRepository.save(notification);
    }
}
