package com.weble.linkedhouse.reservation.service;

import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.reservation.dto.request.CancelReservationRequest;
import com.weble.linkedhouse.reservation.entity.Reservation;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    public void delete(Long reservationId, CancelReservationRequest request) {

        Reservation reservation = reservationRepository.findByReservationId(reservationId);

        reservationRepository.delete(reservation);
    }
}
