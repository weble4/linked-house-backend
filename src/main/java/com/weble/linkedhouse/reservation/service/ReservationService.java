package com.weble.linkedhouse.reservation.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.reservation.dto.request.ReservationRequest;
import com.weble.linkedhouse.reservation.dto.response.ReservationResponse;
import com.weble.linkedhouse.reservation.entity.Reservation;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    public List<ReservationResponse> findByCustomerId(Long customerId) {
        List<ReservationResponse> responses = reservationRepository.findByCustomerId(customerId).stream()
                .map(ReservationResponse::from).toList();
        return responses;
    }

    public List<ReservationResponse> findByRentalId(Long rentalId) {
        List<ReservationResponse> responses = reservationRepository.findByRentalId(rentalId).stream()
                .map(ReservationResponse::from).toList();
        return responses;
    }

    /* 게스트나 호스트가 개별 예약에 대한 상세조회
    public ReservationResponse findByReservationId(Long reservationId) {
        ReservationResponse response = ReservationResponse.from(reservationRepository.findByReservationId(reservationId));
        return response;
    }

     */

    public void createReservation(UserDetailsImpl userDetails, ReservationRequest request, Long rentalId) {
        Long customerId = userDetails.getUserId();

        if(customerRepository.findById(customerId) == null) {
            throw new NotExistCustomer();
        }

        reservationRepository.save(request.toEntity());
    }
}
