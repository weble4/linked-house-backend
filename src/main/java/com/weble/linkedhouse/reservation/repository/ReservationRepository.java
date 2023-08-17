package com.weble.linkedhouse.reservation.repository;

import com.weble.linkedhouse.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 예약자의 ID로 예약된 모든 예약목록 리스트
    List<Reservation> findByCustomerId(Long customerId);

    // 호스트의 숙박업소 ID로 예약받은 모든 리스트
    List<Reservation> findByRentalId(Long rentalId);

    // 예약 건에 대한 단일 조회
    Reservation findByReservationId(Long reservationId);

}
