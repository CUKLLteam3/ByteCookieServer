package com.example.bytecookie.domain.education.service;

import com.example.bytecookie.domain.education.entity.SavedEducation;
import com.example.bytecookie.domain.education.repository.SavedEducationRepository;
import com.example.bytecookie.domain.user.entity.UserInfo;
import com.example.bytecookie.domain.user.repository.UserInfoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional (readOnly=true)
public class SavedEducationService {

    private final SavedEducationRepository savedEducationRepository;
    private final UserInfoRepository userInfoRepository;

    @Transactional
    // 찜 저장 (목록/상세에서 호출 가능)
    public void saveEducation(Long userId, String educationId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id=" + userId));

        boolean exists = savedEducationRepository.existsByUserIdAndEducationId(user.getId(), educationId);
        if (!exists) {
            SavedEducation savedEducation = SavedEducation.builder()
                    .userId(user.getId())  // FK 대신 그냥 ID만 저장
                    .educationId(educationId.trim())  // 🔹 입력값은 안전하게 trim() 처리 권장
                    .build();
            savedEducationRepository.save(savedEducation);
        }
    }

    @Transactional
    public void deleteEducation(Long userId, String educationId) {
        if (educationId == null || educationId.trim().isEmpty()) {
            throw new IllegalArgumentException("educationId is required");
        }
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id=" + userId));

        long deleted = savedEducationRepository.deleteByUserIdAndEducationId(user.getId(), educationId.trim());
        if (deleted == 0) {
            throw new IllegalStateException("삭제할 데이터가 없습니다. (userId=" + userId + ", educationId=" + educationId + ")");
        }
    }



    // 찜 목록 조회
    public List<String> getSavedEducationIds(Long userId) {
        return savedEducationRepository.findByUserId(userId).stream()
                .map(SavedEducation::getEducationId)
                .toList();
    }

}