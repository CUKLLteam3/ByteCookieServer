package com.example.login.service;

import com.example.login.dto.UserResponseDto;
import com.example.login.dto.UserUpdateDto;
import com.example.login.entity.User;
import com.example.login.jwt.JwtUtil;
import com.example.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 내 정보 조회
    public UserResponseDto getMyInfo(String token) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        return new UserResponseDto(
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getBirthdate(),
                user.getGender(),
                user.getAddress()
        );
    }

    // 내 정보 수정
    public void updateMyInfo(String token, UserUpdateDto dto) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getBirthdate() != null) user.setBirthdate(dto.getBirthdate());
        if (dto.getGender() != null) user.setGender(dto.getGender());
        if (dto.getAddress() != null) user.setAddress(dto.getAddress());

        userRepository.save(user);
    }


    // 회원 탈퇴
    public void deleteMyAccount(String token) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        userRepository.delete(user);
    }


}
