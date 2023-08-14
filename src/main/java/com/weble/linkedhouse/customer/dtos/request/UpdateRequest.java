package com.weble.linkedhouse.customer.dtos.request;

import com.weble.linkedhouse.customer.entity.constant.PublicAt;
import lombok.Getter;

@Getter
public class UpdateRequest {

    private String nickname;
    private String phoneNum;
    private PublicAt publicAt;

    private UpdateRequest(String nickname, String phoneNum, PublicAt publicAt) {
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.publicAt = publicAt;
    }
}
