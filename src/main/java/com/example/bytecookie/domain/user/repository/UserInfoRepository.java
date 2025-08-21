package com.example.bytecookie.domain.user.repository;


import com.example.bytecookie.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    // 예: Optional<UserInfo> findByEmail(String email); 등 추가 가능
}
