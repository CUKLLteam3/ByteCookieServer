package com.example.bytecookie.domain.mypage;

import com.example.bytecookie.domain.education.service.EducationQueryService;
import com.example.bytecookie.domain.education.service.SavedEducationService;
import com.example.bytecookie.dto.education.EducationDetailDto;
import com.example.bytecookie.dto.education.EducationListItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final SavedEducationService savedEducationService;
    private final EducationQueryService educationQueryService;

    public Object getSaved(Long userId, String expand, Integer limit) {
        List<String> ids = savedEducationService.getSavedEducationIds(userId);
        if (limit != null && limit > 0 && limit < ids.size()) {
            ids = ids.subList(0, limit);
        }

        switch (expand == null ? "ids" : expand.toLowerCase()) {
            case "summary":
                return ids.stream()
                        .map(this::toSummarySafe)
                        .filter(Objects::nonNull)
                        .toList();

            case "detail":
                return ids.stream()
                        .map(this::toDetailSafe)
                        .filter(Objects::nonNull)
                        .toList();

            default:
                return ids;
        }
    }

    public void deleteSavedEducation(Long userId, String educationId) {
        savedEducationService.deleteEducation(userId, educationId);
    }

    // ----- 내부 유틸 -----

    private EducationListItemDto toSummarySafe(String id) {
        try {
            var d = educationQueryService.detail(id, null); // 카드/병행 자동 시도
            return EducationListItemDto.builder()
                    .id(d.getId())
                    .title(d.getTitle())
                    .startDate(d.getStartDate())
                    .endDate(d.getEndDate())
                    .address(d.getAddress())
                    .trainTarget(d.getTrainTarget())
                    .build();
        } catch (Exception e) {
            // 상세 실패하면 해당 항목 스킵 (null 반환)
            return null;
        }
    }

    private EducationDetailDto toDetailSafe(String id) {
        try {
            return educationQueryService.detail(id, null);
        } catch (Exception e) {
            return null;
        }
    }
}
