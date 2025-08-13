package com.example.bytecookie.controller;

import com.example.bytecookie.domain.resume.service.ResumeService;
import com.example.bytecookie.dto.resume.ResumeDto;
import com.example.bytecookie.dto.resume.ResumeRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/resume/{userId}")
@RequiredArgsConstructor

/**
 *  이력서를 위한 REST 컨트롤러
 */

public class ResumeController {
    private final ResumeService resumeService;


    // 저장
    @PostMapping("/save")
    public ResponseEntity<Void> save(@PathVariable Long userId, @RequestBody ResumeRequestDto res) {
        resumeService.save(userId, res.getRes());
        return ResponseEntity.ok().build();
    }

    // 조회
    @GetMapping("/read")
    public ResponseEntity<List<ResumeDto>> getMyResume(@PathVariable Long userId) {
        List<ResumeDto> resumes = resumeService.findByUserId(userId);
        return ResponseEntity.ok(resumes);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateResume(@PathVariable Long userId, @RequestBody String res) {
        resumeService.update(userId, res);
        return ResponseEntity.ok().build();
    }


}
