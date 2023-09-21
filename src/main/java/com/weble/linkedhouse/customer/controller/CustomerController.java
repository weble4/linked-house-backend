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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000" )
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<ProfileDto> getCustomer(@PathVariable Long customerId) {
        ProfileDto customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok().body(customer);
    }

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

    @PostMapping("/logout")
    public ResponseEntity<TokenDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        customerService.logout(userDetails);
        return ResponseEntity.ok().body(TokenDto.of("", "", 0L));
    }

    @GetMapping("/activate-state")
    public ResponseEntity<String> certified(@RequestParam Long customerId) {
        customerService.certifiedEmail(customerId);
        return ResponseEntity.ok("인증에 성공하였습니다");
    }

    //False - email 존재 x, True - email 존재 o
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean value = customerService.checkEmail(email);
        return ResponseEntity.ok().body(value);
    }

    @PostMapping("/add-role")
    public ResponseEntity<String> applyHost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        customerService.applyHost(userDetails);
        return ResponseEntity.ok().body("완료 되었습니다.");
    }

    // Password 잊어버렸을 때 찾기
    @PatchMapping("/password")
    public void changePassword(@RequestBody PasswordFindRequest passwordFindRequest) {
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
                                    @RequestPart @Valid UpdateRequest updateRequest,
                                    @RequestPart MultipartFile image) {

        return customerService.updateProfile(userDetails, updateRequest, image);
    }

    //토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestHeader("refresh") String refreshToken) {
        TokenDto newAccessToken = customerService.reissue(refreshToken);
        return ResponseEntity.ok().body(newAccessToken);
    }

    @PostMapping("/admin")
    public ResponseEntity<String> adminRoleUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        customerService.addRoleAdmin(userDetails);
        return ResponseEntity.ok().body("어드민 유저 전환");
    }
}
