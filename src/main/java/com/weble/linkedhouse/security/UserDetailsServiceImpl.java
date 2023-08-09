package com.weble.linkedhouse.security;

import com.weble.linkedhouse.customer.dtos.ProfileDtos;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String customerEmail) throws UsernameNotFoundException {

        ProfileDtos profileDtos = profileRepository.findByCustomerCustomerEmail(customerEmail).map(ProfileDtos::from)
                .orElseThrow(NotExistCustomer::new);

        return new UserDetailsImpl(profileDtos);
    }
}
