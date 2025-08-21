package com.example.bytecookie.dto.recruit.list;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoredRecruitmentItem extends RecruitmentItem {
    private int similarityScore; // 유사도 점수 (25, 50, 75, 100)
}
