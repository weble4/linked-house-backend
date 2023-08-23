package com.weble.linkedhouse.payment.service;

import com.weble.linkedhouse.exception.AlreadyPayReservationException;
import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.exception.NotExistPayment;
import com.weble.linkedhouse.exception.NotExistReservation;
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
    private final ReservationRepository reservationRepository;
    private final HouseRepository houseRepository;

    // 결제 단건 조회
    public PaymentResponseDto findByPaymentId(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(NotExistPayment::new);
        PaymentResponseDto response = PaymentResponseDto.from(payment);
        return response;
    }

    // 결제 전건 조회
    public List<PaymentResponseDto> findByReservationCustomerCustomerId(UserDetailsImpl userDetails) {
        return paymentRepository.findByReservationCustomerCustomerId(userDetails.getUserId())
                .stream().map(PaymentResponseDto::from).toList();
    }

    // 결제 요청
    @Transactional
    public void save(PaymentRequestDto request, Long reservationId) {

        if (paymentRepository.findByReservationReservationId(reservationId).isPresent()) {
            throw new AlreadyPayReservationException();
        }

        House house = houseRepository.findById(request.getRentalId())
                .orElseThrow(NotExistHouseException::new);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotExistReservation::new);
        reservation.payComplete();

        Payment payment = Payment.builder()
                .house(house)
                .reservation(reservation)
                .price(request.getPrice())
                .build();

        paymentRepository.save(payment);
    }

}
