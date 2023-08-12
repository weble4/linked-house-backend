package com.weble.linkedhouse.customer.dtos;


import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.PublicAt;
import lombok.Getter;

@Getter
public class ProfileDto {

    private CustomerDto customerDto;

    private String nickname;

    private String gender;

    private String birthDay;

    private String phoneNum;

    private String imagePath;

    private PublicAt publicAt;

    public ProfileDto(CustomerDto customerDto, String nickname, String gender, String birthDay, String phoneNum, String imagePath, PublicAt publicAt) {
        this.customerDto = customerDto;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDay = birthDay;
        this.phoneNum = phoneNum;
        this.imagePath = imagePath;
        this.publicAt = publicAt;
    }

    public static ProfileDto of(CustomerDto customerDto, String nickname, String gender, String birthDay, String phoneNum, String imagePath, PublicAt publicAt) {
        return new ProfileDto(customerDto, nickname, gender, birthDay, phoneNum, imagePath, publicAt);
    }

    public static ProfileDto from(CustomerProfile entity) {
        return new ProfileDto(
                CustomerDto.from(entity.getCustomer()),
                entity.getNickname(),
                entity.getGender(),
                entity.getBirthDay(),
                entity.getPhoneNum(),
                entity.getImagePath(),
                entity.getPublicAt()
        );
    }

    public CustomerProfile toEntity() {
        return CustomerProfile.of(customerDto.toEntity(), nickname, gender, birthDay, phoneNum, imagePath);
    }

}
