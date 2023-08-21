package com.weble.linkedhouse.payment.repository;

import com.weble.linkedhouse.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 숙박업소 번호로 전체 조회 (호스트)
    List<Payment> findByCustomerId(Long customerId);
}
