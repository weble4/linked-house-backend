package com.weble.linkedhouse.house.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.house.dto.request.HostHouseSaveRequest;
import com.weble.linkedhouse.house.dto.request.UpdateHouseRequestDto;
import com.weble.linkedhouse.house.dto.response.HouseResponseDto;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.HouseImage;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import com.weble.linkedhouse.house.repository.HouseImageRepository;
import com.weble.linkedhouse.house.repository.HouseRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import com.weble.linkedhouse.util.storage.ObjectStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseHostService {

    private final HouseRepository houseRepository;
    private final HouseImageRepository houseImageRepository;
    private final CustomerRepository customerRepository;
    private final ObjectStorage objectStorage;

    public Page<HouseResponseDto> findMyRegistrationHouseList(UserDetailsImpl userDetails, Pageable pageable) {

        Long customerId = userDetails.getUserId();
        customerRepository.findByIdWithProfile(customerId).orElseThrow(NotExistCustomer::new);

        return houseRepository.findByCustomerCustomerId(customerId, pageable).map(HouseResponseDto::from);
    }

    public HouseResponseDto findMyRegistrationHouse(Long rentalId) {
        return houseRepository.findByIdWithCustomer(rentalId).map(HouseResponseDto::from)
                .orElseThrow(NotExistHouseException::new);
    }

    @Transactional
    public void registrationHouse(UserDetailsImpl userDetails, HostHouseSaveRequest request, List<MultipartFile> images) {

        Long customerId = userDetails.getUserId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(NotExistCustomer::new);

        House save = houseRepository.save(request.toEntity(customer));

        List<String> imagePathList = objectStorage.uploadListImage(images);

        List<HouseImage> houseImages = imagePathList.stream()
                .map(imagePath -> HouseImage.of(save, imagePath))
                .collect(Collectors.toList());
        houseImageRepository.saveAll(houseImages);
    }


    @Transactional
    public HouseResponseDto updateHouse(List<MultipartFile> images, UpdateHouseRequestDto update) {

        House house = houseRepository.findByIdWithCustomer(update.getRentalId())
                .orElseThrow(NotExistHouseException::new);

        house.updateHouse(
                update.getDescription(),
                update.getMaxCapacity(),
                update.getMinCapacity(),
                update.getPrice(),
                update.getAutoReservation(),
                update.getRoom(),
                update.getBed(),
                update.getBathRoom()
        );

        List<String> fileUrls = house.getImagePath()
                .stream().map(HouseImage::getImagePath)
                .collect(Collectors.toList());
        house.getImagePath().clear();

        houseImageRepository.deleteByHouseRentalId(house.getRentalId());
        objectStorage.deleteListImages(fileUrls);

        List<HouseImage> newList = new ArrayList<>();

        if (images != null) {
            List<String> imageList = objectStorage.uploadListImage(images);
            for (String imagePath : imageList) {
                HouseImage newImage = HouseImage.of(house, imagePath);
                newList.add(newImage);
                house.addImagePath(newImage);
            }
            houseImageRepository.saveAll(newList);
        }
        return HouseResponseDto.from(house);
    }

    @Transactional
    public void updateReservation(UpdateHouseRequestDto update, Long rentalId) {

        if (update.getRentalId() != rentalId) {
            throw new NotExistHouseException();
        }

        House house = houseRepository.findByIdWithCustomer(update.getRentalId())
                .orElseThrow(NotExistHouseException::new);

        house.updateHouse(
                update.getDescription(),
                update.getMaxCapacity(),
                update.getMinCapacity(),
                update.getPrice(),
                update.getAutoReservation(),
                update.getRoom(),
                update.getBed(),
                update.getBathRoom()
        );
    }

    @Transactional
    public void delete(Long rentalId) {
        House house = houseRepository.getReferenceById(rentalId);
        houseImageRepository.deleteByHouseRentalId(house.getRentalId());
        houseRepository.deleteById(house.getRentalId());
    }
}
