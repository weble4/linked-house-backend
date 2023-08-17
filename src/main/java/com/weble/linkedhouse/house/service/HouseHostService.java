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
import com.weble.linkedhouse.house.repository.HouseImageRepository;
import com.weble.linkedhouse.house.repository.HouseRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import com.weble.linkedhouse.util.CreateFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseHostService {

    private final HouseRepository houseRepository;
    private final HouseImageRepository houseImageRepository;
    private final CustomerRepository customerRepository;

    public Page<HouseResponseDto> findMyRegistrationHouseList(UserDetailsImpl userDetails, Pageable pageable) {

        Long customerId = userDetails.getUserId();
        customerRepository.findById(customerId).orElseThrow(NotExistCustomer::new);

        return houseRepository.findByCustomerCustomerId(customerId, pageable).map(HouseResponseDto::from);
    }

    public HouseResponseDto findMyRegistrationHouse(Long rentalId) {
        return houseRepository.findById(rentalId).map(HouseResponseDto::from)
                .orElseThrow(NotExistHouseException::new);
    }

    @Transactional
    public void registrationHouse(UserDetailsImpl userDetails, HostHouseSaveRequest request, List<MultipartFile> images) {

        Long customerId = userDetails.getUserId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(NotExistCustomer::new);

        House save = houseRepository.save(request.toEntity(customer));

        CreateFile createFile = new CreateFile();
        List<String> imagePathList = createFile.saveHouseImage(images, customerId);

        List<HouseImage> houseImages = houseImageList(imagePathList, save);
        houseImageRepository.saveAll(houseImages);
    }


    //개선 방법이 있다면 개선필요!!
    @Transactional
    public HouseResponseDto updateHouse(List<MultipartFile> images, UpdateHouseRequestDto update) {
        House house = houseRepository.findById(update.getRentalId())
                .orElseThrow(NotExistHouseException::new);

        house.updateHouse(
                update.getMaxCapacity(),
                update.getMinCapacity(),
                update.getPrice(),
                update.getAutoReservation(),
                update.getRoom(),
                update.getBed(),
                update.getBathRoom()
        );

        CreateFile createFile = new CreateFile();

        List<HouseImage> houseImageList = houseImageRepository.findByHouseRentalId(house.getRentalId());

        List<String> collect = houseImageList.stream().map(HouseImage::getImagePath)
                .collect(Collectors.toList());

        //이미지파일이 null 일때 이미지 삭제
        if (images == null) {
            houseImageList.clear();
        } else {
            List<String> imageList = createFile.saveHouseImage(images, house.getRentalId());
            houseImageList.clear();
            for (String imagePath : imageList) {
                HouseImage newImage = HouseImage.of(house, imagePath);
                house.addImagePath(newImage);
                houseImageList.add(newImage);
            }
        }
        //새로인 이미지 파일 저장
        houseImageRepository.saveAll(houseImageList);
        //실제 기존 파일들 삭제
        createFile.deleteImageFile(collect);

        return HouseResponseDto.from(house);
    }

    @Transactional
    public void delete(Long rentalId) {
        House house = houseRepository.getReferenceById(rentalId);
        houseImageRepository.deleteByHouseRentalId(house.getRentalId());
        houseRepository.deleteById(house.getRentalId());
    }


    private List<HouseImage> houseImageList(List<String> imagePathList, House house) {

        List<HouseImage> houseImages = new ArrayList<>();

        for (String imagePath : imagePathList) {
            HouseImage houseImage = HouseImage.of(house, imagePath);
            houseImages.add(houseImage);
        }
        return houseImages;
    }
}
