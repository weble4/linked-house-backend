package com.weble.linkedhouse.util.config.oauth;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final CustomerRepository customerRepository;

    // 사용자 정보를 담은 객체 반환
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    private Customer saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        Optional<Customer> customerOptional = customerRepository.findByCustomerEmail(email);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.updateName(name);
            return customerRepository.save(customer);
        } else {
            Customer newCustomer = Customer.builder()
                    .customerEmail(email)
                    .customerPw(createRandomPassword())
                    .build();
            return customerRepository.save(newCustomer);
        }
    }

    // PW 대체 랜덤 UUID 생성
    private String createRandomPassword() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
