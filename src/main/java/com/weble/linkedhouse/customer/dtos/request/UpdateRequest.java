package com.weble.linkedhouse.customer.dtos.request;

import com.weble.linkedhouse.customer.entity.constant.PublicAt;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateRequest {

    private String nickname;
    private String phoneNum;
    private String image;
    private PublicAt publicAt;

    @Builder
    private UpdateRequest(String nickname, String phoneNum, String image, PublicAt publicAt) {
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.image = image;
        this.publicAt = publicAt;
    }
}
