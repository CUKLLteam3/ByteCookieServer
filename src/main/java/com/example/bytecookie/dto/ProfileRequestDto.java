package com.example.bytecookie.dto;



import com.example.bytecookie.domain.profile.entity.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {
    private String name;
    private Integer birthYear;
    private Profile.Gender gender;
    private String phone;
    private String region;
    private String transports;
    private String commuteDistance;
    private String healthStatus;
    private String workHours;
    private String workDays;
    private String considerations;
    private String career;
    private String certificates;
    private String digitalSkill;
    private String jobPreferences;
    private String workTypes;
    private String wagePreference;
    private String educationInterests;
    private String supportNeeds;
    private String appHelps;
    private String opinion;
}
