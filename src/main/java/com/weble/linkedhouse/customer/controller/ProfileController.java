package com.weble.linkedhouse.customer.controller;

import com.weble.linkedhouse.customer.dtos.ProfileDtos;
import com.weble.linkedhouse.customer.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    //TODO : Security 설정 이후 만들기.

    @GetMapping
    public ProfileDtos getCustomerProfile() {
        profileService.getCustomerProfile();
        return null;
    }

    @PatchMapping
    public ProfileDtos updateProfile() {
        profileService.updateProfile();
        return null;
    }

}
