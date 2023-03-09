package com.cheil.repository;

import com.cheil.domain.ApplicationUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/09
 */
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmail(String email);
}
