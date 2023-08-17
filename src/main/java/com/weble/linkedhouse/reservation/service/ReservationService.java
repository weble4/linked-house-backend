package com.weble.linkedhouse.reservation.service;

import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.reservation.dto.request.ReservationRequest;
import com.weble.linkedhouse.reservation.dto.response.ReservationResponse;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    public List<ReservationResponse> findByCustomerCustomerId(Long customerId) {
        List<ReservationResponse> responses = reservationRepository.findByCustomerCustomerId(customerId).stream()
                .map(ReservationResponse::from).toList();
        return responses;
    }

    public List<ReservationResponse> findByHouseRentalId(Long rentalId) {
        List<ReservationResponse> responses = reservationRepository.findByHouseRentalId(rentalId).stream()
                .map(ReservationResponse::from).toList();
        return responses;
    }

    // 게스트나 호스트가 개별 예약에 대한 상세조회
    public ReservationResponse findById(Long reservationId) {
        ReservationResponse response = ReservationResponse.from(reservationRepository.findById(reservationId).orElseThrow(NotExistHouseException::new));
        return response;
    }



    public void createReservation(UserDetailsImpl userDetails, ReservationRequest request, Long rentalId) {
        Long customerId = userDetails.getUserId();

        customerRepository.findById(customerId).orElseThrow(NotExistHouseException::new);

        reservationRepository.save(request.toEntity());
    }
}
