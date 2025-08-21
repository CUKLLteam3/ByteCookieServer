package com.example.bytecookie.domain.user.entity;

import com.example.bytecookie.domain.education.entity.SavedEducation;
import com.example.bytecookie.domain.recruit.entity.SavedRecruit;
import com.example.bytecookie.domain.resume.entity.Resume;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;


    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<SavedRecruit> savedRecruits;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Resume resume;

    @Column(name = "nickname",nullable = false, length = 50)
    private String nickname;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "region")
    private String region;

    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private User.Gender gender;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private User.Role role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ✅ enum 그대로 사용
    public enum Gender {
        남성, 여성
    }

    public enum Role {
        USER, ADMIN
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedEducation> savedEducations = new ArrayList<>();

}
