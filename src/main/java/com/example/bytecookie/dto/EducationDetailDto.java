package com.example.bytecookie.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EducationDetailDto {
    private String id;            // trprId
    // 개요
    private String title;         // title
    private String subTitle;      // subTitle
    // 일정
    private String startDate;     // traStartDate (yyyy-MM-dd)
    private String endDate;       // traEndDate   (yyyy-MM-dd)
    private String address;       // address
    // 수강정보
    private Integer regCourseMan; // regCourseMan
    private Integer yardMan;      // yardMan
    private Integer courseFee;    // courseMan (정수)
    private String  trainTarget;  // 훈련유형 이름
    private String  trainTargetCd;// 훈련유형 코드 (C0061 등)
    // 기관
    private String providerName;  // instNm (없으면 list의 subTitle)
    private String managerName;   // managerNm
    private String telNo;         // telNo
    private String homepage;      // 고용24 URL or 응답값
}