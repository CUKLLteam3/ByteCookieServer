package com.example.bytecookie.dto.recruit.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentSearchRequest {
    private String ncsCdLst;      // 직무 코드
    private String hireTypeLst;  // 고용형태 코드
    private String workRgnLst;   // 근무 지역 코드
    private String recrutSe;     // 채용 구분 코드

}
