package com.weble.linkedhouse.notification.controller;

import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;

    @PostMapping
    public Notification notification(
            @RequestParam Long customerId,
            @RequestParam NotificationType notificationType,
            @RequestParam String notificationContent){
            return notificationService.createNotification(customerID, notificationType, notificationCotent);
        }
    )
}
