package com.weble.linkedhouse.admin.service;


import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import com.weble.linkedhouse.exception.NotExistReview;
import com.weble.linkedhouse.review.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.review.entity.FeedbackHost;
import com.weble.linkedhouse.review.repository.FeedbackHostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private FeedbackHostRepository feedbackHostRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Test
    @Transactional
    @DisplayName(value = "호스트 리뷰 전체 조회 테스트")
    public void findAllByHostReviewTest() throws Exception {

        // given
        Customer customer = customerRepository.save(createUser());
        Long customerId = customer.getCustomerId();

        for (int i = 0; i < 10; i++) {
            FeedbackHost of = FeedbackHost.of(customer, customer, "제목", "내용", i, i);
            feedbackHostRepository.save(of);
        }

        // when  // PageRequest.of
        Page<HostReviewResponse> allByHostReview = adminService.findAllByHostReview(customerId, PageRequest.of(0, 9));
        List<HostReviewResponse> hostReviewResponses = allByHostReview.get().toList();


        // then
        assertThat(hostReviewResponses.size()).isEqualTo(9);
        assertThat(hostReviewResponses.get(1).getTitle()).isEqualTo("제목");
    }

    @Test
    @Transactional
    @DisplayName(value = "호스트 리뷰 단일 조회 테스트")
    public void findByHostReviewIdTest() throws Exception {

        // given
        String title = "1번제목";
        String content = "1번내용";
        int attitude = 10;
        int damageDegree = 10;

        Customer customer = customerRepository.save(createUser());
        Customer writer = customerRepository.save(createUser());

        FeedbackHost hostReview = FeedbackHost.of(customer, writer, title, content, attitude, damageDegree);
        FeedbackHost reviewTest = feedbackHostRepository.save(hostReview);
        // H2 DB 에다가 위에 선언된 값들을 넣어줄 것(H2용 Entity 를 만들 것)

        // when
        HostReviewResponse byHostReviewId = adminService.findByHostReviewId(reviewTest.getFeedbackHostId());
        // adminService.findByHostReviewId 를 통해, 리포지토리에서 조회한 값이 null인 경우임.
        // 그래서, byHostReviewId 에서 데이터를 추출해도 null 이므로, 값 자체가 조회되지 않음.

        // then
        assertEquals(damageDegree, byHostReviewId.getDamageDegree());
        assertEquals(attitude, byHostReviewId.getAttitude());
        assertEquals(title, byHostReviewId.getTitle());
        assertEquals(content, byHostReviewId.getContent());
    }

    @Test
    @Transactional
    @DisplayName(value = "호스트 리뷰 단일 삭제 테스트")
    public void deleteHostReviewIdTest() throws Exception {
        // given
        String title = "1번제목";
        String content = "1번내용";
        int attitude = 10;
        int damageDegree = 10;

        Customer customer = customerRepository.save(createUser());
        Customer writer = customerRepository.save(createUser());

        FeedbackHost hostReview = FeedbackHost.of(customer, writer, title, content, attitude, damageDegree);
        FeedbackHost reviewTest = feedbackHostRepository.save(hostReview);

        // when
        adminService.deleteHostReviewId(reviewTest.getFeedbackHostId());

        // then
        assertThrows(NotExistReview.class, () -> adminService.findByHostReviewId(reviewTest.getFeedbackHostId()));
    }

        Customer createUser () {
            return Customer.of(
                    "sample@mail.com",
                    passwordEncoder.encode("abc123"),
                    Set.of(Role.ROLE_CUSTOMER)
            );
        }
    }

