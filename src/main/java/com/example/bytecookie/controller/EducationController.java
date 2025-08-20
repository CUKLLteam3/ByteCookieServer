package com.example.bytecookie.controller;

import com.example.bytecookie.domain.education.service.EducationQueryService;
import com.example.bytecookie.domain.education.service.SavedEducationService;
import com.example.bytecookie.dto.EducationDetailDto;
import com.example.bytecookie.dto.EducationListItemDto;
import com.example.bytecookie.dto.SaveEducationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/educations")
@RequiredArgsConstructor
public class EducationController {


    // 목록(외부 API 호출)용
    private final EducationQueryService educationQueryService;
    private final SavedEducationService savedEducationService;

    // ✅ 목록
    @GetMapping
    public List<EducationListItemDto> getAllEducations(
            @RequestParam(required = false, defaultValue = "both") String program, // ← 기본 both
            @RequestParam(required = false) String area1,   // "11,41" 허용
            @RequestParam(required = false) String gbn,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String ncs1,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String sortCol,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String st,      // yyyyMMdd (선택)
            @RequestParam(required = false) String en       // yyyyMMdd (선택)
    ) {
        return educationQueryService.list(area1, gbn, type, ncs1, keyword, sort, sortCol, page, size, st, en);
    }

    // ✅ 상세 (현재는 기존 서비스 사용)
    @GetMapping("/{id}")
    public EducationDetailDto getEducationById(@PathVariable String id,
                                               @RequestParam(required = false, defaultValue = "both") String program) {
        return educationQueryService.detail(id, program);
    }

    // ✅ 찜 저장 (목록/상세에서 사용 가능)
    @PostMapping("/save")
    public ResponseEntity<Void> saveEducation(@RequestBody SaveEducationRequest request) {
        savedEducationService.saveEducation(request.getUserId(), request.getEducationId());
        return ResponseEntity.ok().build();
    }
}