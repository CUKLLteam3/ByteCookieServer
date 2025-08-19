package com.example.login.dto;


import com.example.login.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDate birthdate;
    private User.Gender gender;
    private String address;
}