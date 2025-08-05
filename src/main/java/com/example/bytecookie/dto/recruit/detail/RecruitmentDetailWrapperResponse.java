package com.example.bytecookie.dto.recruit.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecruitmentDetailWrapperResponse {
    private int resultCode;
    private String resultMsg;
    private RecruitmentDetailResponse result;  // 아래 DTO로 중첩 매핑
}

