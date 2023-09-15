package com.weble.linkedhouse.security;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.entity.Customer;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails, OAuth2User {

    private ProfileDto customer;
    private Map<String, Object> attributes;

    public UserDetailsImpl(ProfileDto profile) {
        this.customer = profile;
    }

    public UserDetailsImpl(ProfileDto profile, Map<String , Object> attributes) {
        this.customer = profile;
        this.attributes = attributes;
    }

    public Long getUserId() {
        return customer.getCustomerDto().getCustomerId();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return customer.getCustomerDto().getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
