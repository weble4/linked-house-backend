package com.weble.linkedhouse.customer.dtos;


import com.weble.linkedhouse.customer.entity.CustomerProfile;
import lombok.Getter;

@Getter
public class ProfileDtos {

    private CustomerDto customerDto;

    private String nickname;

    private String gender;

    private String birthDate;

    private String phoneNum;

    public ProfileDtos(CustomerDto customerDto, String nickname, String gender, String birthDate, String phoneNum) {
        this.customerDto = customerDto;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
    }

    public static ProfileDtos of(CustomerDto customerDto, String nickname, String gender, String birthDate, String phoneNum) {
        return new ProfileDtos(customerDto, nickname, gender, birthDate, phoneNum);
    }

    public static ProfileDtos from(CustomerProfile entity) {
        return new ProfileDtos(
                CustomerDto.from(entity.getCustomer()),
                entity.getNickname(),
                entity.getGender(),
                entity.getBirthDate(),
                entity.getPhoneNum()
        );
    }

    public CustomerProfile toEntity() {
        return CustomerProfile.of(customerDto.toEntity(), nickname, gender, birthDate, phoneNum);
    }

}
