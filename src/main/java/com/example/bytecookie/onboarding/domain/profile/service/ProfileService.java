package com.example.onboarding.domain.profile.service;



import com.example.onboarding.dto.ProfileRequestDto;
import com.example.onboarding.dto.ProfileResponseDto;
import com.example.onboarding.domain.profile.entity.Profile;
import com.example.onboarding.domain.profile.repository.ProfileRepository;
import com.example.onboarding.domain.profile.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserInfoRepository userInfoRepository;

    // 저장/수정
    // 저장
    public ProfileResponseDto saveProfile(Long userId, ProfileRequestDto dto) {
        // user_info 에 유저가 있어야 함
        if (!userInfoRepository.existsById(userId)) {
            throw new RuntimeException("해당 유저가 존재하지 않습니다.");
        }

        // 프로필이 이미 있는지 확인
        Optional<Profile> optionalProfile = profileRepository.findByUserId(userId);
        Profile profile;

        if (optionalProfile.isPresent()) {
            // ✅ 이미 있으면 수정
            profile = optionalProfile.get();
        } else {
            // ✅ 없으면 새로 생성 (최초 작성)
            profile = Profile.builder().userId(userId).build();
        }

        // 값 세팅
        profile.setName(dto.getName());
        profile.setBirthYear(dto.getBirthYear());
        profile.setGender(dto.getGender());
        profile.setPhone(dto.getPhone());
        profile.setRegion(dto.getRegion());
        profile.setCommuteDistance(dto.getCommuteDistance());
        profile.setHealthStatus(dto.getHealthStatus());
        profile.setWorkHours(dto.getWorkHours());
        profile.setWorkDays(dto.getWorkDays());
        profile.setCareer(dto.getCareer());
        profile.setDigitalSkill(dto.getDigitalSkill());
        profile.setWagePreference(dto.getWagePreference());
        profile.setOpinion(dto.getOpinion());

        Profile saved = profileRepository.save(profile);
        return toResponse(saved);
    }


    // 조회
    public ProfileResponseDto getProfile(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
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
