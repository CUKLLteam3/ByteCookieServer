package com.example.bytecookie.domain.recruit.service;

import com.example.bytecookie.domain.recruit.entity.SavedRecruit;
import com.example.bytecookie.domain.user.entity.User;
import com.example.bytecookie.domain.recruit.SavedRecruitRepository;
import com.example.bytecookie.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedRecruitService {

    private final SavedRecruitRepository savedRecruitRepository;
    private final UserRepository userRepository;

    // 저장
    public void save(Long userId, Long sn) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다"));

        boolean exists = savedRecruitRepository.findByUserIdAndSn(user, sn).isPresent();
        if (!exists) {
            SavedRecruit saved = new SavedRecruit();
            saved.setUserId(user);
            saved.setSn(sn);
            savedRecruitRepository.save(saved);
        }
    }

    // 조회
    public List<Long> getSnList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다"));

        return savedRecruitRepository.findAllByUserId(user)
                .stream()
                .map(SavedRecruit::getSn)
                .toList();
    }

    // 삭제
    @Transactional
    public void delete(Long userId, Long sn) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다"));

        savedRecruitRepository.deleteByUserIdAndSn(user, sn);
    }
}
