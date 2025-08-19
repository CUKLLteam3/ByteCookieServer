package com.example.bytecookie.domain.resume.service;

import com.example.bytecookie.domain.resume.ResumeRepository;
import com.example.bytecookie.domain.resume.entity.Resume;
import com.example.bytecookie.domain.user.entity.User;
import com.example.bytecookie.domain.user.repository.UserRepository;
import com.example.bytecookie.dto.resume.ResumeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    //저장
    public void save(Long userId, String res) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다"));

        Resume resume = resumeRepository.findByUser(user)
                .orElseGet(() -> {
                    Resume r = new Resume();
                    r.setUser(user); // 필드명이 userId인 점에 맞춤
                    return r;
                });

        resume.setRes(res);
        resumeRepository.save(resume);
        //Resume resume = new Resume();
        //resume.setUser(user);
        //resume.setRes(res);
        //resumeRepository.save(resume);

    }



    // 조회
    public List<ResumeDto> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다"));

        return resumeRepository.findByUser(user).stream()
                .map(r -> new ResumeDto(r.getRes()))
                .toList();

    }

    public void update(Long userId, String res) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다"));

        Resume resume = resumeRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("이력서를 찾을 수 없습니다."));

        resume.setRes(res); // 새 내용/파일명으로 변경
        resumeRepository.save(resume);
    }




}
