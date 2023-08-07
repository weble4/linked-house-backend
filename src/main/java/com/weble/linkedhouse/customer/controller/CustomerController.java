package com.weble.linkedhouse.customer.controller;

import com.weble.linkedhouse.customer.dtos.request.SignupRequest;
import com.weble.linkedhouse.customer.dtos.response.SignupResponse;
import com.weble.linkedhouse.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest singupRequest) {
        SignupResponse signupResponse = customerService.saveUser(singupRequest);
        return ResponseEntity.ok().body(signupResponse);
    }

    //TODO : security 적용 필요.
//    @PostMapping("/login")
//    public String login() {
//
//    }

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
}
