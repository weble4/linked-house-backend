package com.weble.linkedhouse.reservation.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.repository.HouseRepository;
import com.weble.linkedhouse.reservation.dto.request.ReservationRequest;
import com.weble.linkedhouse.reservation.dto.response.ReservationResponse;
import com.weble.linkedhouse.reservation.entity.Reservation;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final HouseRepository houseRepository;

    public List<ReservationResponse> findByCustomerCustomerId(Long customerId) {
        return reservationRepository.findByCustomerCustomerId(customerId).stream()
                .map(ReservationResponse::from).toList();
    }

    public List<ReservationResponse> findByHouseRentalId(Long rentalId) {
        return reservationRepository.findByHouseRentalId(rentalId).stream()
                .map(ReservationResponse::from).toList();
    }

    // 게스트나 호스트가 개별 예약에 대한 상세조회
    public ReservationResponse findById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotExistHouseException::new);
        return ReservationResponse.from(reservation);
    }


    @Transactional
    public void createReservation(UserDetailsImpl userDetails, ReservationRequest request, Long rentalId) {
        Long customerId = userDetails.getUserId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(NotExistHouseException::new);
        House house = houseRepository.findById(rentalId)
                .orElseThrow(NotExistHouseException::new);

        Reservation reservation = Reservation.of(
                house,
                customer,
                request.getCheckinDate(),
                request.getCheckoutDate(),
                request.getReservationNum());

        reservationRepository.save(reservation);
    }
}
