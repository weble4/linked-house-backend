package com.weble.linkedhouse.customer.service;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.dtos.request.UpdateRequest;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileDto getCustomerProfile(UserDetailsImpl userDetails) {
        CustomerProfile profile = profileRepository.findByCustomerCustomerEmail(userDetails.getUsername())
                .orElseThrow(NotExistCustomer::new);
        return ProfileDto.from(profile);
    }

    @Transactional
    public ProfileDto updateProfile(UserDetailsImpl userDetails, UpdateRequest updateRequest) {
        CustomerProfile profile = profileRepository.findByCustomerCustomerEmail(userDetails.getUsername())
                .orElseThrow(NotExistCustomer::new);
        profile.updateProfile(updateRequest);

        return ProfileDto.from(profile);
    }
}
