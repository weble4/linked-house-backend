package com.weble.linkedhouse.admin.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import com.weble.linkedhouse.house.repository.HouseRepository;
import com.weble.linkedhouse.review.dtos.response.CustomerReviewResponse;
import com.weble.linkedhouse.review.entity.FeedbackCustomer;
import com.weble.linkedhouse.review.repository.FeedbackCustomerRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private FeedbackCustomerRepository feedbackCustomerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HouseRepository houseRepository;

    @Test
    @Transactional
    @DisplayName(value = "숙소 리뷰 전체 조회 테스트")
    public void findAllByCustomerReviewTest() throws Exception{

        Customer customer = customerRepository.save(createUser());
        House house = houseRepository.save(createHouse(customer));
        Long customerId = customer.getCustomerId();

        for(int i = 0; i < 10; i++){
            FeedbackCustomer of = FeedbackCustomer.of(customer, house, "제목", "내용", i, i, i);
            feedbackCustomerRepository.save(of);
        }

        Page<CustomerReviewResponse> allByCustomerReview = adminService.findAllByCustomerReview(customerId, PageRequest.of(0,9));
        List<CustomerReviewResponse> customerReviewResponse = allByCustomerReview.get().toList();

        assertThat(customerReviewResponse.size()).isEqualTo(9);
        assertThat(customerReviewResponse.get(1).getTitle()).isEqualTo("제목");
    }

    @Test
    @Transactional
    @DisplayName(value = "숙소 리뷰 단일 조회 테스트")
    public void findByCustomerReviewIdTest() throws Exception {
        String title = "1번제목";
        String content = "1번내용";
        int scoreClean = 10;
        int scoreCommunication = 10;
        int scoreSatisfaction = 10;

        Customer customer = customerRepository.save(createUser());
        House house = houseRepository.save(createHouse(customer));

        FeedbackCustomer customerReview = FeedbackCustomer.of(customer, house, title, content, scoreClean, scoreCommunication, scoreSatisfaction);
        FeedbackCustomer reviewTest = feedbackCustomerRepository.save(customerReview);

        CustomerReviewResponse byCustomerReviewId = adminService.findByCustomerReviewId(reviewTest.getFeedbackCustomerId());

        assertEquals(scoreSatisfaction, byCustomerReviewId.getScoreSatisfaction());
        assertEquals(scoreCommunication, byCustomerReviewId.getScoreCommunication());
        assertEquals(scoreClean, byCustomerReviewId.getScoreClean());

        assertEquals(title, byCustomerReviewId.getTitle());
        assertEquals(content, byCustomerReviewId.getContent());

    }
    @Test
    @Transactional
    @DisplayName(value = "호스트 리뷰 단일 삭제 테스트")
    public void deleteCustomerReviewIdTest() throws Exception{
        String title = "1번제목";
        String content = "1번내용";
        int scoreClean = 10;
        int scoreCommunication = 10;
        int scoreSatisfaction = 10;

        Customer customer = customerRepository.save(createUser());
        House house = houseRepository.save(createHouse(customer));

        FeedbackCustomer customerReview = FeedbackCustomer.of(customer, house, title, content, scoreClean, scoreCommunication, scoreSatisfaction);
        FeedbackCustomer reviewTest = feedbackCustomerRepository.save(customerReview);

        adminService.deleteCustomerReviewId(reviewTest.getFeedbackCustomerId());//리뷰 삭제

        Optional<FeedbackCustomer> deletedReview = feedbackCustomerRepository.findById(reviewTest.getFeedbackCustomerId());

        assertThat(deletedReview).isEmpty();//리뷰 됐는지 마지막 확인
    }
    Customer createUser () {
        return Customer.of(
                "sample@mail.com",
                passwordEncoder.encode("abc123"),
                Set.of(Role.ROLE_CUSTOMER)
        );
    }
    House createHouse(Customer customer) {
        return House.of(
                customer,
                "description",
                5,
                1,
                10000,
                "서울",
                "서울",
                AutoReservation.AUTO,
                3,
                3,
                3
        );
    }
}