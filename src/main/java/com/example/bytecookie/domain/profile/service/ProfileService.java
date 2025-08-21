package com.example.bytecookie.domain.profile.service;



import com.example.bytecookie.domain.user.entity.UserInfo;
import com.example.bytecookie.dto.ProfileRequestDto;
import com.example.bytecookie.dto.ProfileResponseDto;
import com.example.bytecookie.domain.profile.entity.Profile;
import com.example.bytecookie.domain.profile.repository.ProfileRepository;
import com.example.bytecookie.domain.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserInfoRepository userInfoRepository;

    // 저장/수정
    public ProfileResponseDto saveProfile(Long userId, ProfileRequestDto dto) {
        // user_info 에 유저가 있어야 함
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다. id=" + userId));

        // 프로필이 이미 있는지 확인
        Optional<Profile> optionalProfile = profileRepository.findByUserInfo(user);
        Profile profile = optionalProfile.orElseGet(() -> Profile.builder()
                .userInfo(user)   // ✅ FK 객체로 설정
                .build());

        // 값 세팅
        profile.setName(dto.getName());
        profile.setBirthYear(dto.getBirthYear());
        profile.setGender(dto.getGender());
        profile.setPhone(dto.getPhone());
        profile.setRegion(dto.getRegion());
        profile.setTransports(dto.getTransports());
        profile.setCommuteDistance(dto.getCommuteDistance());
        profile.setHealthStatus(dto.getHealthStatus());
        profile.setWorkHours(dto.getWorkHours());
        profile.setWorkDays(dto.getWorkDays());
        profile.setConsiderations(dto.getConsiderations());
        profile.setCareer(dto.getCareer());
        profile.setCertificates(dto.getCertificates());
        profile.setDigitalSkill(dto.getDigitalSkill());
        profile.setJobPreferences(dto.getJobPreferences());
        profile.setWorkTypes(dto.getWorkTypes());
        profile.setWagePreference(dto.getWagePreference());
        profile.setEducationInterests(dto.getEducationInterests());
        profile.setSupportNeeds(dto.getSupportNeeds());
        profile.setAppHelps(dto.getAppHelps());
        profile.setOpinion(dto.getOpinion());

        Profile saved = profileRepository.save(profile);
        return toResponse(saved);
    }

    // 조회
    public ProfileResponseDto getProfile(Long userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다. id=" + userId));

        Profile profile = profileRepository.findByUserInfo(user)
                .orElseThrow(() -> new RuntimeException("해당 유저의 프로필이 없습니다."));
        return toResponse(profile);
    }

    private ProfileResponseDto toResponse(Profile profile) {
        return ProfileResponseDto.builder()
                .name(profile.getName())
                .birthYear(profile.getBirthYear())
                .gender(profile.getGender())
                .phone(profile.getPhone())
                .region(profile.getRegion())
                .transports(profile.getTransports())
                .commuteDistance(profile.getCommuteDistance())
                .healthStatus(profile.getHealthStatus())
                .workHours(profile.getWorkHours())
                .workDays(profile.getWorkDays())
                .considerations(profile.getConsiderations())
                .career(profile.getCareer())
                .certificates(profile.getCertificates())
                .digitalSkill(profile.getDigitalSkill())
                .jobPreferences(profile.getJobPreferences())
                .workTypes(profile.getWorkTypes())
                .wagePreference(profile.getWagePreference())
                .educationInterests(profile.getEducationInterests())
                .supportNeeds(profile.getSupportNeeds())
                .appHelps(profile.getAppHelps())
                .opinion(profile.getOpinion())
                .build();
    }
}

