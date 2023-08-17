package com.weble.linkedhouse.reservation.repository;

import com.weble.linkedhouse.reservation.entity.CancelReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelReservationRepository extends JpaRepository<CancelReservation, Long> {

}
