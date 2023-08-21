package com.weble.linkedhouse.payment.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.NotExistPayment;
import com.weble.linkedhouse.payment.dto.request.PaymentRequestDto;
import com.weble.linkedhouse.payment.dto.response.PaymentResponseDto;
import com.weble.linkedhouse.payment.entity.Payment;
import com.weble.linkedhouse.payment.repository.PaymentRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;


    public PaymentResponseDto findById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(NotExistPayment::new);

        PaymentResponseDto dto = PaymentResponseDto.from(payment);
        return dto;
    }

    public List<PaymentResponseDto> findByCustomerId(Long customerId) {

        customerRepository.findById(customerId).orElseThrow(NotExistCustomer::new);

        List<PaymentResponseDto> payments = paymentRepository.findByCustomerId(customerId).stream()
                .map(PaymentResponseDto::from).collect(Collectors.toList());

        return payments;
    }

    @Transactional
    public void savePayment(UserDetailsImpl userDetails, PaymentRequestDto paymentRequest) {

        Long userId = userDetails.getUserId();

        customerRepository.findById(userId).orElseThrow(NotExistCustomer::new);

        Payment payment = paymentRequest.toEntity();

        paymentRepository.save(payment);
    }
}
