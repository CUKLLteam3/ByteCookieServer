package com.example.bytecookie.dto;

import com.example.bytecookie.domain.login.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateDto {
    private String password;  // 수정 시 새 비밀번호 (선택)
    private String name;
    private String phone;
    private LocalDate birthdate;
    private User.Gender gender;
    private String address;
}