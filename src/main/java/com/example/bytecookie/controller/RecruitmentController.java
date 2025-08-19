package com.example.bytecookie.controller;

import com.example.bytecookie.domain.recruit.service.SavedRecruitService;
import com.example.bytecookie.domain.user.entity.User;
import com.example.bytecookie.domain.login.repository.UserRepository;
import com.example.bytecookie.dto.recruit.detail.RecruitmentDetailResponse;
import com.example.bytecookie.dto.recruit.list.RecruitmentItem;
import com.example.bytecookie.domain.recruit.service.RecruitmentService;
import com.example.bytecookie.dto.recruit.list.RecruitmentSearchRequest;
import com.example.bytecookie.dto.recruit.list.ScoredRecruitmentItem;
import com.example.bytecookie.dto.recruit.save.SaveRecruitRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    private final SavedRecruitService savedRecruitService;
    private final UserRepository userRepository; // 임시 유저 아이디 테스트용

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
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody SaveRecruitRequest request) {
        savedRecruitService.save(request.getUserId(), request.getSn());
        return ResponseEntity.ok().build();
    }

    // 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<Long>> getSavedSn(@PathVariable Long userId) {
        List<Long> savedSnList = savedRecruitService.getSnList(userId);
        return ResponseEntity.ok(savedSnList);
    }

    // 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody SaveRecruitRequest request) {
        savedRecruitService.delete(request.getUserId(), request.getSn());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createUser(
            @RequestParam String nickname,
            @RequestParam(required = false) String ageGroup,
            @RequestParam(required = false) String region
    ) {
        User user = new User();
        user.setNickname(nickname);
        user.setAgeGroup(ageGroup);
        user.setRegion(region);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved.getId());
    }

}

