package com.example.login.controller;

import com.example.login.dto.UserResponseDto;
import com.example.login.dto.UserUpdateDto;
import com.example.login.jwt.JwtUtil;
import com.example.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    // 내 정보 조회 (JWT 필요)
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // "Bearer " 잘라내기
        return ResponseEntity.ok(userService.getMyInfo(token));
    }

    // 내 정보 수정 (JWT 필요)
    @PutMapping("/me")
    public ResponseEntity<String> updateMyInfo(@RequestHeader("Authorization") String authHeader,
                                               @RequestBody UserUpdateDto dto) {
        String token = authHeader.substring(7);
        userService.updateMyInfo(token, dto);
        return ResponseEntity.ok("회원정보 수정 완료");
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMyAccount(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        userService.deleteMyAccount(token);
        return ResponseEntity.ok("회원 탈퇴 완료");
    }

}

