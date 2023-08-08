package com.weble.linkedhouse.security;

import com.weble.linkedhouse.customer.dtos.ProfileDtos;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private ProfileDtos customer;

    public UserDetailsImpl(ProfileDtos profile) {
        this.customer = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(customer.getCustomerDto().getRole().getReason()));
    }

    @Override
    public String getPassword() {
        return customer.getCustomerDto().getCustomerPw();
    }

    @Override
    public String getUsername() {
        return customer.getCustomerDto().getCustomerEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
