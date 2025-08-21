package com.example.bytecookie.domain.education.repository;

import com.example.bytecookie.domain.education.entity.SavedEducation;
import com.example.bytecookie.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedEducationRepository extends JpaRepository<SavedEducation, Long> {

    // 특정 유저의 저장된 교육 목록
    List<SavedEducation> findByUser(User user);
    // 특정 유저가 특정 교육을 이미 저장했는지 확인
    boolean existsByUserAndEducationId(User user, String educationId);
    // 특정 유저의 특정 교육 삭제
    long deleteByUserAndEducationId(User user, String educationId);
}
