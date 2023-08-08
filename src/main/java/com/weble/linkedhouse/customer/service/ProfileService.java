package com.weble.linkedhouse.customer.service;

import com.weble.linkedhouse.customer.dtos.ProfileDtos;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final CustomerRepository customerRepository;
    private final ProfileRepository profileRepository;

    public ProfileDtos getCustomerProfile() {
        return null;
    }

    public void updateProfile() {
    }
}
