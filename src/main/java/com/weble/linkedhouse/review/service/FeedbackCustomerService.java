package com.weble.linkedhouse.review.service;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.exception.NotExistReview;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.repository.HouseRepository;
import com.weble.linkedhouse.review.dtos.request.CustomerReviewRequest;
import com.weble.linkedhouse.review.dtos.response.CustomerReviewResponse;
import com.weble.linkedhouse.review.entity.FeedbackCustomer;
import com.weble.linkedhouse.review.repository.FeedbackCustomerRepository;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackCustomerService {

    private final FeedbackCustomerRepository feedbackCustomerRepository;
    private final HouseRepository houseRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerReviewResponse createCustomerReview(Long rentalId,
                                                       UserDetailsImpl userDetails,
                                                       CustomerReviewRequest request) {

        House house = houseRepository.findById(rentalId).orElseThrow(NotExistHouseException::new);
        Customer customer = customerRepository.findById(userDetails.getUserId()).orElseThrow(NotExistCustomer::new);

        FeedbackCustomer review = FeedbackCustomer.of(
                customer,
                house,
                request.getTitle(),
                request.getContent(),
                request.getScoreClean(),
                request.getScoreCommunication(),
                request.getScoreSatisfaction()
        );
        review.calculate();

        FeedbackCustomer saveReview = feedbackCustomerRepository.save(review);

        return CustomerReviewResponse.from(saveReview);
    }

    public CustomerReviewResponse findByCustomerReviewId(Long feedbackCustomerId) {
        return feedbackCustomerRepository.findById(feedbackCustomerId).map(CustomerReviewResponse::from)
                .orElseThrow(NotExistReview::new);

//        FeedbackCustomer feedbackCustomer = feedbackCustomerRepository.findById(feedbackCustomerId)
//                .orElseThrow(NotExistReview::new);
//        return CustomerReviewResponse.from(feedbackCustomer);
    }

    public Page<CustomerReviewResponse> findAllByCustomerReview(Long customerId, Pageable pageable) {
        return feedbackCustomerRepository.findAllByCustomerCustomerId(customerId, pageable)
                .map(CustomerReviewResponse::from);
    }

    public Page<CustomerReviewResponse> findAllHouseReview(Long rentalId, Pageable pageable) {
        return feedbackCustomerRepository.findAllByHouseRentalId(rentalId, pageable)
                .map(CustomerReviewResponse::from);
    }

    @Transactional
    public CustomerReviewResponse updateCustomerReview(Long feedbackCustomerId, CustomerReviewRequest request) {

        FeedbackCustomer feedbackCustomer = feedbackCustomerRepository.findById(feedbackCustomerId)
                .orElseThrow(NotExistReview::new);

        feedbackCustomer.updateReview(
                request.getContent(),
                request.getScoreClean(),
                request.getScoreCommunication(),
                request.getScoreSatisfaction());

        feedbackCustomer.calculate();

        return CustomerReviewResponse.from(feedbackCustomer);
    }

    @Transactional
    public void deleteCustomerReview(Long feedbackCustomerId) {
        feedbackCustomerRepository.deleteById(feedbackCustomerId);
    }
}
