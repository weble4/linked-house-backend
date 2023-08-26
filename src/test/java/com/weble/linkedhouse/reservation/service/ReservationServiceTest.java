package com.weble.linkedhouse.reservation.service;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import com.weble.linkedhouse.house.repository.HouseRepository;
import com.weble.linkedhouse.reservation.dto.request.ReservationRequest;
import com.weble.linkedhouse.reservation.dto.response.ReservationResponse;
import com.weble.linkedhouse.reservation.entity.Reservation;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("예약자 기준 예약 목록 리스트 확인")
    void findByCustomerCustomerIdTest() {
        Customer customer = createCustomer(); // 예약자 Customer
        customerRepository.save(customer);

        Customer host = createHost(); // 호스트 Customer
        customerRepository.save(host);

        House house = createHouse(host);
        houseRepository.save(house);

        Reservation reservation = createReservation(house, customer);
        reservationRepository.save(reservation);

        List<ReservationResponse> reservationList = reservationService.findByCustomerCustomerId(customer.getCustomerId());

        assertThat(reservationList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("등록 숙박업소에 예약된 목록 확인")
    void findByHouseRentalIdTest() {

        Customer customer = createCustomer();
        customerRepository.save(customer);

        Customer host = createHost(); // 호스트 Customer
        customerRepository.save(host);

        House house = createHouse(host);
        houseRepository.save(house);

        Reservation reservation = createReservation(house, customer);
        reservationRepository.save(reservation);

        long rentalId = house.getRentalId();
        LocalDateTime checkin = LocalDateTime.of(2023, 8, 24, 00, 00, 0000);

        List<ReservationResponse> reservationList = reservationService.findByHouseRentalId(rentalId);

        assertThat(reservationList.size()).isEqualTo(1);
        assertThat(reservationList.get(0).getCheckinDate()).isEqualTo(checkin); // index 는 0부터
    }

    @Test
    @DisplayName("개별 예약 확인")
    void findByIdTest() {

        Customer customer = createCustomer();
        customerRepository.save(customer);

        Customer host = createHost(); // 호스트 Customer
        customerRepository.save(host);

        House house = createHouse(host);
        houseRepository.save(house);

        Reservation reservation = createReservation(house, customer);
        reservationRepository.save(reservation);

        long reservationId = reservation.getReservationId();
        LocalDateTime checkin = LocalDateTime.of(2023, 8, 24, 00, 00, 0000);

        ReservationResponse response = reservationService.findById(reservationId);

        assertThat(response.getCheckinDate()).isEqualTo(checkin);
    }

    @Test
    @DisplayName("예약 생성 확인")
    void createReservationTest() {
        Customer host = createHost(); // 호스트 Customer
        customerRepository.save(host);

        // CustomerProfile hostProfile = createProfile(host);

        Customer customer = createCustomer();
        customerRepository.save(customer);

        CustomerProfile customerProfile = profileRepository.save(createProfile(customer));
        customer.setCustomerProfile(customerProfile);

        House house = createHouse(host);
        houseRepository.save(house);

        ReservationRequest request = ReservationRequest.of(
                house.getRentalId(),
                LocalDateTime.of(2023, 8, 24, 00, 00, 0000),
                LocalDateTime.of(2023, 8, 31, 00, 00, 0000),
                5
        );

        UserDetailsImpl userDetails = new UserDetailsImpl(ProfileDto.from(customerProfile));
        reservationService.createReservation(userDetails, request, house.getRentalId());


        long customerId = customer.getCustomerId();
        Reservation reservation = reservationRepository.findByCustomerCustomerId(customerId).get(0);
        long reservationId = reservation.getReservationId();

        assertThat(reservationService.findByCustomerCustomerId(customerId).get(0).getReservationId())
                .isEqualTo(reservationId);
        assertThat(reservationService.findByCustomerCustomerId(customerId).get(0).getCustomerId())
                .isEqualTo(customerId);
    }

    Customer createCustomer() {
        return Customer.of(
                "sample@mail.com",
                passwordEncoder.encode("abc123"),
                Set.of(Role.ROLE_CUSTOMER)
        );
    }

    Customer createHost() {
        return Customer.of(
                "sample2@mail.com",
                passwordEncoder.encode("abc123"),
                Set.of(Role.ROLE_HOST)
        );
    }

    CustomerProfile createProfile(Customer customer) {
        return CustomerProfile.of(
                customer,
                "nickname",
                "man",
                "921223",
                "010-1111-1234",
                null
        );
    }

    House createHouse(Customer customer) {
        return House.of(
                customer,
                "description",
                5,
                1,
                10000,
                "서울",
                "서울",
                AutoReservation.AUTO,
                3,
                3,
                3
        );
    }

    Reservation createReservation(House house, Customer customer) {
        return Reservation.of(
                house,
                customer,
                LocalDateTime.of(2023, 8, 24, 00, 00, 0000),
                LocalDateTime.of(2023, 8, 31, 00, 00, 0000),
                5
        );
    }
}
