package com.cheil.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/09
 */
@Table(name = "users")
@Getter
@NoArgsConstructor
@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role; //나중에 필요하면 리스트로. (1:N)

    @Builder
    public ApplicationUser(String email, String password, String firstName, String lastName,
        String role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
