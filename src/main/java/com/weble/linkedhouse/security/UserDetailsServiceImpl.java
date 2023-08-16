package com.weble.linkedhouse.security;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String customerEmail) throws UsernameNotFoundException {
        ProfileDto profile = customerRepository.findByCustomerEmailWithCustomerProfile(customerEmail)
                .map(Customer::getCustomerProfile)
                .map(ProfileDto::from)
                .orElseThrow(NotExistCustomer::new);
        return new UserDetailsImpl(profile);
    }
}
