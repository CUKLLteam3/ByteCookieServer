package com.example.bytecookie.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EducationListItemDto {
    private String id;          // 외부 과정 ID(trprId)
    private String title;       // 과정명(title)
    private String startDate;   // 시작일(traStartDate, yyyy-MM-dd)
    private String endDate;     // 종료일(traEndDate, yyyy-MM-dd)
    private String address;     // 주소(address)
    private String trainTarget; // ✅훈련유형 '이름' (trainTarget) — "대상"이 아니라 유형 명칭
}