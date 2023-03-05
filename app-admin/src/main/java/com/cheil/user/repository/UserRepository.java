package com.cheil.user.repository;

import com.cheil.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where (:id is null or u.id = :id) " +
            "and (:email is null or u.email = :email)" +
            "and (:firstName is null or u.firstName = :firstName)" +
            "and (:lastName is null or u.lastName = :lastName)")
    public List<User> findUsersByParams(
            @Param("id") Long id,
            @Param("email") String email,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName
    );
}
