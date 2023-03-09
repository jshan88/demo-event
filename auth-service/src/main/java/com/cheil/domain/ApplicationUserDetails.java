package com.cheil.domain;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/09
 */
@Getter
@RequiredArgsConstructor
public class ApplicationUserDetails implements UserDetails {

    private final ApplicationUser applicationUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(applicationUser.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return applicationUser.getPassword();
    }

    @Override
    public String getUsername() {
        return applicationUser.getEmail();
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
