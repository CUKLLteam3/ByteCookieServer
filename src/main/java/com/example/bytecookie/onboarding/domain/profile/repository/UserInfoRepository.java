package com.example.onboarding.domain.profile.repository;


import com.example.onboarding.domain.profile.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    // 예: Optional<UserInfo> findByEmail(String email); 등 추가 가능
}
