package com.weble.linkedhouse.customer.controller;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.dtos.request.LoginRequest;
import com.weble.linkedhouse.customer.dtos.request.PasswordFindRequest;
import com.weble.linkedhouse.customer.dtos.request.SignupRequest;
import com.weble.linkedhouse.customer.dtos.request.UpdateRequest;
import com.weble.linkedhouse.customer.dtos.response.LoginResponse;
import com.weble.linkedhouse.customer.dtos.response.SignupResponse;
import com.weble.linkedhouse.customer.service.CustomerService;
import com.weble.linkedhouse.security.UserDetailsImpl;
import com.weble.linkedhouse.security.jwt.token.TokenDto;
import com.weble.linkedhouse.security.jwt.token.TokenRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest signupRequest) {
        log.info("signup Controller");
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

    @PostMapping("/add-role")
    public ResponseEntity<String> applyHost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        customerService.applyHost(userDetails);
        return ResponseEntity.ok().body("완료 되었습니다.");
    }

    // Password 잊어버렸을 때 찾기
    @PatchMapping("/password")
    public void findPassword(@RequestBody PasswordFindRequest passwordFindRequest) {
        customerService.findPassword(passwordFindRequest);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> requestWithdrawal(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        customerService.withdrawal(userDetails);
        return ResponseEntity.ok("탈퇴 요청 하였습니다.");
    }

    @GetMapping("/profiles")
    public ProfileDto getCustomerProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return customerService.getCustomerProfile(userDetails);
    }

    @PatchMapping("/profiles")
    public ProfileDto updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestPart UpdateRequest updateRequest,
                                    @RequestPart MultipartFile image) {
        return customerService.updateProfile(userDetails, updateRequest, image);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TokenDto reissue = customerService.reissue(tokenRequestDto, userDetails);
        return ResponseEntity.ok().body(reissue);
    }
}
