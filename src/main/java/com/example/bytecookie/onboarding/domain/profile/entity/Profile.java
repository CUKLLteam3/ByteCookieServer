package com.example.onboarding.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Long userId; // users.user_id 참조 , JWT로부터 얻은 user_id 저장

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
    private java.sql.Timestamp createdAt;

    @Column(updatable = false, insertable = false)
    private java.sql.Timestamp updatedAt;


    public enum Gender {
        남성, 여성
    }

}
