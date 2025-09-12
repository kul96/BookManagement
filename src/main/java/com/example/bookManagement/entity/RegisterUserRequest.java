package com.example.bookManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterUserRequest {
    private String username;
    private String password;
    private Role role;
}
