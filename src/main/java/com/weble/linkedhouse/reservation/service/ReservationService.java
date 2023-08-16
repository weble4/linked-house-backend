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

    public ReservationResponse findByReservationId(Long reservationId) {
        ReservationResponse response = ReservationResponse.from(reservationRepository.findByReservationId(reservationId));
        return response;
    }

    public void createReservation(UserDetailsImpl userDetails, ReservationRequest request) {
        Long customerId = userDetails.getUserId();

        Customer customer = customerRepository.findById(customerId).orElseThrow(NotExistCustomer::new);

        Reservation reservation = reservationRepository.save(request.toEntity());

        reservationRepository.save(reservation);
    }
}
