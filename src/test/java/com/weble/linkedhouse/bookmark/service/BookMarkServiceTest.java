package com.weble.linkedhouse.bookmark.service;

import com.weble.linkedhouse.bookmark.repository.BookMarkRepository;
import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.dtos.request.SignupRequest;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@Transactional
@SpringBootTest
class BookMarkServiceTest {


    @Autowired
    BookMarkService bookMarkService;

    @Autowired
    BookMarkRepository bookMarkRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("북마크 추가 및 북마크 리스트 확인")
    void addBookmarkAndFindListTest() {

        Customer save = customerRepository.save(createUser());
        CustomerProfile profile = profileRepository.save(createProfile(save));
        save.setCustomerProfile(profile);
        UserDetailsImpl userDetails = new UserDetailsImpl(ProfileDto.from(profile));
        System.out.println("userDetails = " + userDetails);

        bookMarkService.addBookmark(userDetails, 1L );

        String tableName =  deleteTableName(save.getCustomerEmail(), save.getCustomerId());

        assertThat(bookMarkRepository.isTableExists(tableName)).isTrue();
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

    Customer createUser() {
        return Customer.of(
                "sameple@mail.com",
                passwordEncoder.encode("abc123"),
                Set.of(Role.ROLE_CUSTOMER)
        );
    }

    SignupRequest createSignup() {
        return SignupRequest.of(
                "sample@mail.com",
                "abc123",
                Set.of(Role.ROLE_CUSTOMER),
                "nickname",
                "man",
                "010-1111-1234",
                "921223"
        );
    }

    private String deleteTableName(String customerName, Long customerId) {
        int idx = customerName.indexOf("@");
        String origin = customerName.substring(0, idx);
        return "bookmark_" + origin + "_" + customerId;
    }
}