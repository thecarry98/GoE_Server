package com.application.learnenglish.model.entity;

import com.application.learnenglish.model.entity.converter.RoleConverter;
import com.application.learnenglish.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @NotBlank
    @Column(name = "username", unique = true)
    private String userName;
    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String avatar;
    @Convert(converter = RoleConverter.class)
    @Column(length = 13, nullable = false)
    private Role role;

}
