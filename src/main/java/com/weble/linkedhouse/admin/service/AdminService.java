package com.weble.linkedhouse.admin.service;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.entity.BannedCustomer;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.customer.repository.BannedCustomerRepository;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.NotExistReview;
import com.weble.linkedhouse.notification.dtos.NoticeAll;
import com.weble.linkedhouse.notification.entity.Notification;
import com.weble.linkedhouse.notification.repository.NotificationRepository;
import com.weble.linkedhouse.review.dtos.response.HostReviewResponse;
import com.weble.linkedhouse.review.repository.FeedbackHostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final NotificationRepository notificationRepository;
    private final CustomerRepository customerRepository;
    private final FeedbackHostRepository feedbackHostRepository;
    private final BannedCustomerRepository bannedCustomerRepository;


    @Transactional
    public void informNotification(NoticeAll request) {
        List<Notification> notificationList = new ArrayList<>();
        for (int i = 0; i < request.getCustomerId().size(); i++) {
            Customer customer = customerRepository.findById(request.getCustomerId().get(i))
                    .orElseThrow(NotExistCustomer::new);

            Notification notification = Notification.of(customer, request.getNotificationContent());
            notificationList.add(notification);
        }
        notificationRepository.saveAll(notificationList);
    }

    public Page<HostReviewResponse> findAllByHostReview(Long customerId, Pageable pageable) {
        return feedbackHostRepository.findAllByCustomerCustomerId(customerId, pageable)
                .map(HostReviewResponse::from);
    }

    public HostReviewResponse findByHostReviewId(Long feedbackHostId) {
        return feedbackHostRepository.findById(feedbackHostId)
                .map(HostReviewResponse::from)   // 리포지토리에서 조회한 값이, null이 아닌 경우(값이 있는 경우) 리턴되는 구문
                .orElseThrow(NotExistReview::new);  // 리포지토리에서 조회한 값이, null인 경우(값이 없는 경우) 리턴되는 구문
    }

    @Transactional
    public void deleteHostReviewId(Long feedbackHostId) {
        feedbackHostRepository.deleteById(feedbackHostId);
    }

    public ProfileDto getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> ProfileDto.from(customer.getCustomerProfile()))
                .orElseThrow(NotExistCustomer::new);
    }

    @Transactional
    public void suspendUser(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(NotExistCustomer::new);
        customer.banned();

        BannedCustomer bannedCustomer = BannedCustomer.of(customerId);
        bannedCustomerRepository.save(bannedCustomer);
    }

    @Transactional
    public void addRoleToCustomer(Long customerId, Role newRole) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(NotExistCustomer::new);

        customer.addRole(newRole);

        customerRepository.save(customer);
    }
    public Page<ProfileDto> findAllCustomers(AdminFilter adminFilter, Pageable pageable) {
        Page<Customer> allCustomers = customerRepository.findAllCustomers(adminFilter, pageable);

        Page<ProfileDto> map = allCustomers.map(customer -> ProfileDto.from(customer.getCustomerProfile()));
    return map;
    }
}
        /*
        List<ProfileDto> profileDtosList = new ArrayList<>();
        for (Customer customer : AllCustomers) {
            ProfileList.add(profileDto.from(customer.getCustomerProfile()))
        }
        return profileDtosList;

        --------------------------------------------------------------------

        return customerRepository.findAllCustomers(adminFilter, pageable)
                    .map(customer -> profileDto.from(customer.getCustomerProfile()));

        */
