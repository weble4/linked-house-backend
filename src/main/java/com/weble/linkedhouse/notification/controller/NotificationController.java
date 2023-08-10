package com.weble.linkedhouse.notification.controller;

import com.weble.linkedhouse.notification.dtos.NotificationCreateRequestDto;
import com.weble.linkedhouse.notification.dtos.NotificationDto;
import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping // 전체내역 조회
    public List<NotificationDto> getAllNotification() {
        List<Notification> notification = notificationService.getAllNotification();
        return notification.stream()
                .map(NotificationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{notificationId}") // 단일내역 조회
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        if (notification != null) {
            NotificationDto notificationDto = NotificationDto.fromEntity(notification);
            return ResponseEntity.ok(notificationDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationCreateRequestDto requestDto) {
        Notification notification = notificationService.createNotification(
                requestDto.getCustomerId(),
                requestDto.getNotificationType(),
                requestDto.getNotificationContent()
        );
        NotificationDto notificationDto = NotificationDto.fromEntity(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationDto);
    }
}