package com.example.bytecookie.domain.user.entity;

import com.example.bytecookie.domain.recruit.entity.SavedRecruit;
import com.example.bytecookie.domain.resume.entity.Resume;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
