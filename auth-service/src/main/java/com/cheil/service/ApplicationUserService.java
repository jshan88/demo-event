package com.cheil.service;

import com.cheil.domain.ApplicationUser;
import com.cheil.domain.ApplicationUserDetails;
import com.cheil.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/08
 */
@RequiredArgsConstructor
@Service
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByEmail(username)
            .orElseThrow(
                () -> new RuntimeException("Member Not Found."));   /// 이거 API CONFIG 데려와서 해.

        return new ApplicationUserDetails(applicationUser);
    }
}
