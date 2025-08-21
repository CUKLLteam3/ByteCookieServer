package com.example.bytecookie.domain.profile.repository;



import com.example.bytecookie.domain.profile.entity.Profile;
import com.example.bytecookie.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserInfo(UserInfo userInfo);
}

