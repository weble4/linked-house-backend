package com.weble.linkedhouse.customer.controller;

import com.weble.linkedhouse.customer.dtos.ProfileDtos;
import com.weble.linkedhouse.customer.dtos.request.LoginRequest;
import com.weble.linkedhouse.customer.dtos.request.SignupRequest;
import com.weble.linkedhouse.customer.dtos.response.LoginResponse;
import com.weble.linkedhouse.customer.dtos.response.SignupResponse;
import com.weble.linkedhouse.customer.service.CustomerService;
import com.weble.linkedhouse.customer.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final ProfileService profileService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest signupRequest) {
        SignupResponse signupResponse = customerService.saveUser(signupRequest);
        return ResponseEntity.ok().body(signupResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse login = customerService.login(request);
        return ResponseEntity.ok().body(login);
    }

    @GetMapping("/activate-state")
    public ResponseEntity<String> activate(@RequestParam Long customerId) {
        customerService.activateAccount(customerId);
        return ResponseEntity.ok("인증에 성공하였습니다");
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> requestWithdrawal() {
        customerService.withdrawl();
        return ResponseEntity.ok("탈퇴 요청 하였습니다.");
    }


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
