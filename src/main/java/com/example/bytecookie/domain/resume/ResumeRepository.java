package com.example.bytecookie.domain.resume;

import com.example.bytecookie.domain.resume.entity.Resume;
import com.example.bytecookie.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByUser(User user);        // 소유자 단건 조회

}
