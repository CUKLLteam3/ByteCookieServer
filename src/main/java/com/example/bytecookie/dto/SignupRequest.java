package com.example.bytecookie.dto;


import com.example.bytecookie.domain.login.entity.User;
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