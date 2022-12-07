package com.sparta.spring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    @Size(min=4, max=10, message="최소4자 이상 10자 이하여야 합니다.")
    @Pattern(regexp = "[a-zA-z0-9]")
    private String username;

    @Column(nullable = false)
    @Size(min=8, max=15, message="최소8자 이상 15자 이하여야 합니다.")
    @Pattern(regexp = "[a-zA-z0-9]")
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
