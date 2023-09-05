package com.weble.linkedhouse.util.config.oauth;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import com.weble.linkedhouse.util.config.oauth.provider.KakaoUserInfo;
import com.weble.linkedhouse.util.config.oauth.provider.NaverUserInfo;
import com.weble.linkedhouse.util.config.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final CustomerRepository customerRepository;

    // 사용자 정보를 담은 객체 반환
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // System.out.println("getAttributes: " + user.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("미지원 OAuth Provider");
        }

        Optional<Customer> customer = customerRepository.findByCustomerEmail(oAuth2UserInfo.getEmail());

        if (customer.isEmpty()) {
            customer = Optional.ofNullable(Customer.builder()
                    .customerEmail(oAuth2UserInfo.getEmail())
                    .customerPw(createRandomPassword())
                    .build());
            customerRepository.save(customer.orElseThrow());
        }

        return new UserDetailsImpl(ProfileDto.from(customer.orElseThrow().getCustomerProfile()), oAuth2User.getAttributes());

    }

    // PW 대체 랜덤 UUID 생성
    private String createRandomPassword() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
