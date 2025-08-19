package com.example.login.dto;

import com.example.login.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String email;
    private String name;
    private String phone;
    private LocalDate birthdate;
    private User.Gender gender;
    private String address;
}