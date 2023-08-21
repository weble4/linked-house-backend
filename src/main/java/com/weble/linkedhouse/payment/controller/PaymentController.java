package com.weble.linkedhouse.payment.controller;

import com.weble.linkedhouse.payment.dto.request.PaymentRequestDto;
import com.weble.linkedhouse.payment.dto.response.PaymentResponseDto;
import com.weble.linkedhouse.payment.service.PaymentService;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 단건 조회
    @GetMapping("/{paymentId}")
    public PaymentResponseDto findByPaymentId(@PathVariable Long paymentId) {

        return paymentService.findByPaymentId(paymentId);
    }

    // 결제 전건 조회
    @GetMapping
    public List<PaymentResponseDto> findByReservationCustomerId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return paymentService.findByReservationCustomerId(userDetails);
    }

    // 결제 요청
    @PostMapping("/{reservationId}")
    public void save(@RequestBody PaymentRequestDto paymentRequest, @PathVariable Long reservationId) {
        paymentService.save(paymentRequest, reservationId);
    }
}
