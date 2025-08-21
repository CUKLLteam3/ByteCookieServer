package com.example.bytecookie.domain.profile.entity;

import com.example.bytecookie.domain.user.entity.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: profile.user_info_id → user_info.user_info_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id", nullable = false)
    private UserInfo userInfo;

    private String name;
    private Integer birthYear;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private String phone;
    private String region;
    private String transports; // "도보,지하철"
    private String commuteDistance;
    private String healthStatus;
    private String workHours;
    private String workDays;
    private String considerations; // "급여 수준,근무시간대"

    @Column(columnDefinition = "TEXT")
    private String career;

    private String certificates; // "운전면허,요양보호사,직접입력값"

    private String digitalSkill;

    private String jobPreferences; // "경비/관리,사무보조,강사"

    private String workTypes;

    private String wagePreference;

    private String educationInterests;

    private String supportNeeds;

    private String appHelps;

    @Column(columnDefinition = "TEXT")
    private String opinion;

    @Column(updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false, insertable = false)
    private LocalDateTime updatedAt;


    public enum Gender {
        남성, 여성
    }

}
