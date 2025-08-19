package com.example.bytecookie.domain.education.repository;

import com.example.bytecookie.domain.education.entity.SavedEducation;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bytecookie.domain.user.entity.UserInfo;

import java.util.List;

@Repository
public interface SavedEducationRepository extends JpaRepository<SavedEducation, Long> {
    List<SavedEducation> findByUserId(Long userId);
    boolean existsByUserIdAndEducationId(Long userId, String educationId);
    long deleteByUserIdAndEducationId(Long userId, String educationId);
}
