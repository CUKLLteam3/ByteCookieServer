package com.example.bytecookie.Controller;

import com.example.bytecookie.domain.mypage.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;

    // 찜 목록 조회
    // expand = ids | summary | detail
    @GetMapping("/saved-educations")
    public ResponseEntity<?> getSavedEducations(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "ids") String expand,
            @RequestParam(required = false) Integer limit // 선택: 최대 개수 제한
    ) {
        return ResponseEntity.ok(mypageService.getSaved(userId, expand, limit));
    }

    // 찜 삭제
    @DeleteMapping("/saved-educations")
    public ResponseEntity<Void> deleteSavedEducation(
            @RequestParam Long userId,
            @RequestParam String educationId
    ) {
        mypageService.deleteSavedEducation(userId, educationId);
        return ResponseEntity.noContent().build();
    }
}