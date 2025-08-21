package com.example.bytecookie.dto.recruit.list;

import com.example.bytecookie.dto.recruit.list.RecruitmentItem;
import lombok.Data;

import java.util.List;

@Data
public class RecruitmentListResponse {

    private String resultCode;
    private String resultMsg;
    private int totalCount;
    private List<RecruitmentItem> result;

}

