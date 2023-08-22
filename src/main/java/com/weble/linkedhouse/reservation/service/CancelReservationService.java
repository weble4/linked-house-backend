package com.weble.linkedhouse.reservation.service;

import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.reservation.entity.CancelReservation;
import com.weble.linkedhouse.reservation.entity.Reservation;
import com.weble.linkedhouse.reservation.repository.CancelReservationRepository;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelReservationService {

    private final CancelReservationRepository cancelReservationRepository;
    private final ReservationRepository reservationRepository;

    public void deleteReservationByCustomer(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NotExistHouseException::new);

        CancelReservation cancelReservation = CancelReservation.of(
                reservation,
                reservation.getCustomer(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getReservationNum()
        );

        cancelReservationRepository.save(cancelReservation);
        reservationRepository.delete(reservation);
    }

    public void deleteReservationByHost(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NotExistHouseException::new);

        CancelReservation cancelReservation = CancelReservation.of(
                reservation,
                reservation.getCustomer(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getReservationNum()
        );

        cancelReservationRepository.save(cancelReservation);
        reservationRepository.delete(reservation);
    }
}
