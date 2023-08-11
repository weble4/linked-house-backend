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

    @GetMapping
    public List<NotificationDto> getAllNotificationDtos() {
        return notificationService.getAllNotificationDtos();
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationDto> getNotificationDtoById(@PathVariable Long notificationId) {
        NotificationDto notificationDto = notificationService.getNotificationDtoById(notificationId);
        if (notificationDto != null) {
            return ResponseEntity.ok(notificationDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationCreateRequestDto requestDto) {
        NotificationDto notificationDto = notificationService.createNotificationDto(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationDto);
    }
}