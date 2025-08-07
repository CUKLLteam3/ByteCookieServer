package com.example.bytecookie.controller;

import com.example.bytecookie.dto.recruit.detail.RecruitmentDetailResponse;
import com.example.bytecookie.dto.recruit.list.RecruitmentItem;
import com.example.bytecookie.domain.recruit.service.RecruitmentService;
import com.example.bytecookie.dto.recruit.list.RecruitmentSearchRequest;
import com.example.bytecookie.dto.recruit.list.ScoredRecruitmentItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 채용 공고 조회를 위한 REST 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    /**
     * 채용 공고 목록 반환 (최신순)
     */
    @GetMapping("/list")
    public List<RecruitmentItem> list() {
        return recruitmentService.getRecruitmentList();
    }

    /**
     * sn 값을 기반으로 상세 채용 공고 조회
     */


    @GetMapping("/detail/{sn}")
    public RecruitmentDetailResponse detail(@PathVariable Long sn) {
        return recruitmentService.getRecruitmentDetail(sn);
    }


    /**
     * 채용 공고 목록 반환 (최신순)
     */
    @PostMapping("/wantlist")
    public List<RecruitmentItem> wantlist(@RequestBody RecruitmentSearchRequest request) {
        return recruitmentService.getRecruitmentwantList(request);
    }

    // 저장

    // 조회

    // 삭제

}

