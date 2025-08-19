package com.example.bytecookie.domain.recruit;

import com.example.bytecookie.domain.recruit.entity.SavedRecruit;
import com.example.bytecookie.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedRecruitRepository extends JpaRepository<SavedRecruit, Long> {

    List<SavedRecruit> findAllByUserId(User user);

    Optional<SavedRecruit> findByUserIdAndSn(User user, Long sn);

    void deleteByUserIdAndSn(User user, Long sn);
}
