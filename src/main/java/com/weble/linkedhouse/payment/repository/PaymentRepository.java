package com.weble.linkedhouse.payment.repository;

import com.weble.linkedhouse.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 결제 요청
    // void save(Payment payment, Long reservationId);

    // 결제 내역에 대한 단건 조회 (게스트)
    Optional<Payment> findByPaymentId(Long reservationId);

    // 결제 내역에 대한 전건 조회 (게스트)
    List<Payment> findByReservationCustomerCustomerId(Long customerId);
}
