package com.weble.linkedhouse.house.service;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import com.weble.linkedhouse.house.dto.FilterKeyword;
import com.weble.linkedhouse.house.dto.SearchKeyword;
import com.weble.linkedhouse.house.dto.request.HostHouseSaveRequest;
import com.weble.linkedhouse.house.dto.request.UpdateHouseRequestDto;
import com.weble.linkedhouse.house.dto.response.HouseResponseDto;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.HouseImage;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import com.weble.linkedhouse.house.repository.HouseImageRepository;
import com.weble.linkedhouse.house.repository.HouseRepository;
import com.weble.linkedhouse.reservation.repository.ReservationRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import com.weble.linkedhouse.util.CreateFile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class HouseServiceTest {

    @Autowired
    HouseHostService houseHostService;

    @Autowired
    HouseCustomerService houseCustomerService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    HouseImageRepository houseImageRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // HouseHostService 관련
    @Test
    @DisplayName("호스트 등록 숙박업소 전체 확인")
    void findMyRegistrationHouseListTest() {
        /* Given */
        // Host 생성
        Customer host = createHost();
        customerRepository.save(host);
        // Host Profile 생성
        CustomerProfile hostProfile = profileRepository.save(createProfile(host));
        host.setCustomerProfile(hostProfile);
        // UserDetails 생성
        UserDetailsImpl userDetails = new UserDetailsImpl(ProfileDto.from(hostProfile));
        // House 생성 (반복마다 새 House 객체 생성 후 저장)
        for (int i = 0; i < 20; i++){
            House house = createHouse(host);
            houseRepository.save(house);
        }

        /* When */
        Page<HouseResponseDto> houseList = houseHostService.findMyRegistrationHouseList(userDetails, PageRequest.of(0, 10));

        /* Then */
        assertThat(houseList.getTotalElements())
                .isEqualTo(20);
        assertThat(houseList.getTotalPages())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("개별 숙박업소 조회")
    void findMyRegistrationHouseTest() {
        /* Given */
        // Host 생성
        Customer host = createHost();
        customerRepository.save(host);
        // Host Profile 생성
        CustomerProfile hostProfile = profileRepository.save(createProfile(host));
        host.setCustomerProfile(hostProfile);
        // UserDetails 생성
        UserDetailsImpl userDetails = new UserDetailsImpl(ProfileDto.from(hostProfile));
        // House 생성
        House house = createHouse(host);
        houseRepository.save(house);

        /* When */
        long rentalId = house.getRentalId();
        HouseResponseDto response = houseHostService.findMyRegistrationHouse(rentalId);

        /* Then */
        assertThat(response.getRentalId())
                .isEqualTo(rentalId);
    }

    @Test
    @Disabled
    @DisplayName("새 숙박업소 저장 확인")
    void registrationHouseTest() {
        /* Given */
        // Host 생성
        Customer host = createHost();
        customerRepository.save(host);
        // Host Profile 생성
        CustomerProfile hostProfile = profileRepository.save(createProfile(host));
        host.setCustomerProfile(hostProfile);
        // UserDetails 생성
        UserDetailsImpl userDetails = new UserDetailsImpl(ProfileDto.from(hostProfile));

        House house = createHouse(host);
        MockMultipartFile mockImage = new MockMultipartFile(
                "imageFile",
                "image.jpg",
                "image/jpg",
                "Image".getBytes()
        );
        List<MultipartFile> images = Collections.singletonList(mockImage);

        HostHouseSaveRequest request = HostHouseSaveRequest.builder()
                .description(house.getDescription())
                .maxCapacity(house.getMaxCapacity())
                .minCapacity(house.getMinCapacity())
                .price(house.getPrice())
                .location(house.getLocation())
                .detailAddress(house.getDetailAddress())
                .autoReservation(house.getAutoReservation())
                .room(house.getRoom())
                .bed(house.getBed())
                .bathRoom(house.getBathRoom())
                .build();

        /* When */
        houseHostService.registrationHouse(userDetails, request, images);

        Page<HouseResponseDto> houseList = houseHostService.findMyRegistrationHouseList(userDetails, PageRequest.of(0, 10));

        /* Then */
        assertThat(houseList.getTotalElements()).isEqualTo(1);
        assertThat(houseList.iterator().next().getDescription()).isEqualTo("description");
    }

    @Test
    @Disabled
    @DisplayName("숙박업소 내용 수정 확인")
    void updateHouseTest() {
        // Host 생성
        Customer customer = customerRepository.save(createHost());
        CustomerProfile hostProfile = profileRepository.save(createProfile(customer));
        customer.setCustomerProfile(hostProfile);
        House saved = houseRepository.save(createHouse(customer));

        // Image Mock 생성
        MockMultipartFile mockImage = new MockMultipartFile(
                "imageFile",
                "image.jpg",
                "image/jpg",
                "Image".getBytes()
        );

        List<MultipartFile> images = Collections.singletonList(mockImage);
        // Image 저장
        CreateFile createFile = new CreateFile();
        List<String> imagePathList = createFile.saveHouseImage(images,customer.getCustomerId());
        List<HouseImage> houseImages = houseImageList(imagePathList, saved);
        houseImageRepository.saveAll(houseImages);

        // 업데이트 로직
        UpdateHouseRequestDto dto = UpdateHouseRequestDto.of(
                saved.getRentalId(),
                "Update",
                5,
                1,
                10000,
                AutoReservation.AUTO,
                3,
                3,
                3
        );

        // 새 Mock Image
        MockMultipartFile newMockImage = new MockMultipartFile(
                "updateFile",
                "update.jpg",
                "update/jpg",
                "Update".getBytes()
        );
        List<MultipartFile> newImages = Collections.singletonList(newMockImage);
        List<String> newImagePathList = createFile.saveHouseImage(newImages, customer.getCustomerId() );
        List<HouseImage> newHouseImages = houseImageList(newImagePathList,saved );
        HouseResponseDto updated = houseHostService.updateHouse(newImages, dto);

        String imagePath = newImages.get(0).getOriginalFilename();
        long rentalId = saved.getRentalId();

        assertThat(houseHostService.findMyRegistrationHouse(rentalId).getDescription()).isEqualTo(updated.getDescription());
        assertThat(houseHostService.findMyRegistrationHouse(rentalId).getImagePath()).isEqualTo(updated.getImagePath());
    }

    // HouseCustomerService 관련
    @Test
    @DisplayName("게스트 숙박업소 조회 기능 확인")
    void findAllHouseTest() {
        Customer customer = createCustomer();
        customerRepository.save(customer);

        Customer host = createHost();
        customerRepository.save(host);

        CustomerProfile hostProfile = profileRepository.save(createProfile(host));
        host.setCustomerProfile(hostProfile);

        for (int i = 0; i < 11; i++) {
            House house = createHouse(host);
            houseRepository.save(house);
        }

        String location = "서울";
        SearchKeyword searchKeyword = new SearchKeyword(); // Setter, 생성자 없어서 테스트 불가

        Page<HouseResponseDto> houses = houseCustomerService.findAllHouse(location, searchKeyword, PageRequest.of(0, 10));

        assertThat(houses.getTotalElements()).isEqualTo(11);
        assertThat(houses.getTotalPages()).isEqualTo(2);
        assertThat(houses.iterator().next().getLocation()).isEqualTo("서울");
    }

    @Test
    @DisplayName("숙박업소 단건 조회")
    void findHouseTest() {
        Customer customer = createCustomer();
        customerRepository.save(customer);

        Customer host = createHost();
        customerRepository.save(host);

        CustomerProfile hostProfile = profileRepository.save(createProfile(host));
        host.setCustomerProfile(hostProfile);

        House house = createHouse(host);
        houseRepository.save(house);

        HouseResponseDto response = houseCustomerService.findHouse(house.getRentalId());

        assertThat(response.getRentalId()).isEqualTo(house.getRentalId());
        assertThat(response.getDescription()).isEqualTo("description");
        assertThat(response.getLocation()).isEqualTo("서울");
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

    List<HouseImage> houseImageList(List<String> imagePathList, House house) {

        List<HouseImage> houseImages = new ArrayList<>();

        for (String imagePath : imagePathList) {
            HouseImage houseImage = HouseImage.of(house, imagePath);
            houseImages.add(houseImage);
        }
        return houseImages;
    }
}