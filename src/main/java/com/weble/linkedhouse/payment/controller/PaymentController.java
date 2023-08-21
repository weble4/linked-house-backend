package com.weble.linkedhouse.payment.controller;

import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.payment.dto.request.PaymentRequestDto;
import com.weble.linkedhouse.payment.dto.response.PaymentResponseDto;
import com.weble.linkedhouse.payment.service.PaymentService;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
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
    private final ReservationRepository reservationRepository;

    @GetMapping("/{paymentId}") // 개별 결제 조회
    public PaymentResponseDto findById(@PathVariable Long paymentId) {
        return paymentService.findById(paymentId);
    }

    @GetMapping// 개인 결제 내역 전체 조회
    public List<PaymentResponseDto> findByReservationCustomerId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return paymentService.findByReservationCustomerId(userDetails);
    }

    @PostMapping("/{rentalId}") // 결제
    public void savePayment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PaymentRequestDto paymentRequestDto, @PathVariable Long rentalId) {
        reservationRepository.findById(rentalId).orElseThrow(NotExistHouseException::new);
        paymentService.savePayment(userDetails, paymentRequestDto);
    }

}
