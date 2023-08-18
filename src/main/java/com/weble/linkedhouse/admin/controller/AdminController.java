package com.weble.linkedhouse.admin.controller;

import com.weble.linkedhouse.admin.service.AdminService;
import com.weble.linkedhouse.notification.dtos.NoticeAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")

public class AdminController {

    private final AdminService adminService;

    @PostMapping("/notifications")
    public ResponseEntity<String> informNotification(@RequestBody NoticeAll request){
        adminService.informNotification(request);
        return ResponseEntity.ok().body("메시지 전달이 성공했습니다.");
    }


}
