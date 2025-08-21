package com.example.bytecookie.domain.education.service;

import com.example.bytecookie.domain.education.entity.SavedEducation;
import com.example.bytecookie.domain.education.repository.SavedEducationRepository;
import com.example.bytecookie.domain.user.entity.User;
import com.example.bytecookie.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedEducationService {

    private final SavedEducationRepository savedEducationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveEducation(Long userId, String educationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id=" + userId));

        boolean exists = savedEducationRepository.existsByUserAndEducationId(user, educationId);
        if (!exists) {
            SavedEducation savedEducation = SavedEducation.builder()
                    .user(user) // ✅ 이제 엔티티로 직접 매핑
                    .educationId(educationId.trim())
                    .createdAt(LocalDateTime.now())
                    .build();

            savedEducationRepository.save(savedEducation);
        }
    }

    @Transactional
    public void deleteEducation(Long userId, String educationId) {
        if (educationId == null || educationId.trim().isEmpty()) {
            throw new IllegalArgumentException("educationId is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id=" + userId));

        long deleted = savedEducationRepository.deleteByUserAndEducationId(user, educationId.trim());
        if (deleted == 0) {
            throw new IllegalStateException("삭제할 데이터가 없습니다. (userId=" + userId + ", educationId=" + educationId + ")");
        }
    }

    // 찜 목록 조회 (ID 리스트만)
    public List<String> getSavedEducationIds(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id=" + userId));

        return savedEducationRepository.findByUser(user).stream()
                .map(SavedEducation::getEducationId)
                .toList();
    }
}
