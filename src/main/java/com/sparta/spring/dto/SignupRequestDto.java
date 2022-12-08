package com.sparta.spring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {
    @Column(nullable = false, unique = true)
    @Size(min=4, max=10, message="최소4자 이상 10자 이하여야 합니다.")
    @Pattern(regexp = "[a-zA-z0-9]")
    private String username;

    @Column(nullable = false)
    @Size(min=8, max=15, message="최소8자 이상 15자 이하여야 합니다.")
    @Pattern(regexp = "[a-zA-z0-9]")
    private String password;

    private String email;

    private boolean admin = false;

    private String adminToken = "";
}
