package com.weble.linkedhouse.reservation.service;

import com.weble.linkedhouse.exception.NotExistReservation;
import com.weble.linkedhouse.reservation.entity.CancelReservation;
import com.weble.linkedhouse.reservation.entity.Reservation;
import com.weble.linkedhouse.reservation.repository.CancelReservationRepository;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CancelReservationService {

    private final CancelReservationRepository cancelReservationRepository;
    private final ReservationRepository reservationRepository;

    public void deleteReservationByCustomer(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NotExistReservation::new);

        CancelReservation cancelReservation = CancelReservation.of(
                reservation.getReservationId(),
                reservation.getCustomer(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getReservationNum()
        );

        cancelReservationRepository.save(cancelReservation);
        reservationRepository.delete(reservation);
    }

    public void deleteReservationByHost(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NotExistReservation::new);

        CancelReservation cancelReservation = CancelReservation.of(
                reservation.getReservationId(),
                reservation.getCustomer(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getReservationNum()
        );

        cancelReservationRepository.save(cancelReservation);
        reservationRepository.delete(reservation);
    }
}
