package com.weble.linkedhouse.notification.controller;

import com.weble.linkedhouse.notification.dtos.NotificationCreateRequest;
import com.weble.linkedhouse.notification.dtos.NotificationDto;
import com.weble.linkedhouse.notification.service.NotificationService;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public Page<NotificationDto> getAllNotification(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable) {
        return notificationService.getAllNotificationDtos(userDetails, pageable);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationDto> getSingleNotification(@PathVariable Long notificationId) {
        NotificationDto notificationDto = notificationService.getSingleNotification(notificationId);
        return ResponseEntity.ok(notificationDto);
    }

    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationCreateRequest requestDto) {
        NotificationDto notification = notificationService.createNotificationDto(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().body("삭제완료");
    }
}