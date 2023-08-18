package com.weble.linkedhouse.customer.dtos.request;

import com.weble.linkedhouse.customer.entity.constant.PublicAt;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateRequest {

    @NotBlank(message = "닉네임을 적어주세요.")
    private String nickname;

    @NotBlank(message ="휴대폰 번호를 적어주세요")
    private String phoneNum;

    @NotBlank(message ="공개 여부를 선택 해야 합니다.")
    private PublicAt publicAt;

    private UpdateRequest(String nickname, String phoneNum, PublicAt publicAt) {
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.publicAt = publicAt;
    }
}
