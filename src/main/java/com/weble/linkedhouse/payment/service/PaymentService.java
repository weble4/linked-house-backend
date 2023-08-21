package com.weble.linkedhouse.payment.service;

import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistPayment;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.repository.HouseRepository;
import com.weble.linkedhouse.payment.dto.request.PaymentRequestDto;
import com.weble.linkedhouse.payment.dto.response.PaymentResponseDto;
import com.weble.linkedhouse.payment.entity.Payment;
import com.weble.linkedhouse.payment.repository.PaymentRepository;
import com.weble.linkedhouse.reservation.entity.Reservation;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final HouseRepository houseRepository;

    // 결제 단건 조회
    public PaymentResponseDto findByPaymentId(Long paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId).orElseThrow(NotExistPayment::new);
        PaymentResponseDto response = PaymentResponseDto.from(payment);
        return response;
    }

    // 결제 전건 조회
    public List<PaymentResponseDto> findByReservationCustomerId(UserDetailsImpl userDetails) {
        List<PaymentResponseDto> responses =  paymentRepository.findByReservationCustomerId(userDetails.getUserId()).stream().map(PaymentResponseDto::from).toList();
        return responses;
    }

    // 결제 요청
    public void save(PaymentRequestDto request, Long reservationId) {
        // paymentRepository.save(Payment.of(request.getRentalId(), request.getReservationId(), request.getPrice(), request.getRequestDay()), reservationId);
        House house = houseRepository.findById(request.getRentalId()).orElseThrow();
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        Payment payment = Payment.builder()
                .house(house)
                .reservation(reservation)
                .price(request.getPrice())
                .requestDay(request.getRequestDay())
                .build();

        paymentRepository.save(payment);
    }

}
