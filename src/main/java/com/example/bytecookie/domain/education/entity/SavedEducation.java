package com.example.bytecookie.domain.education.entity;


import com.example.bytecookie.domain.user.entity.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_education", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "education_id"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SavedEducation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: saved_education.user_id → user_info.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userInfo;

    @Column(name="education_id", nullable=false, length=100)
    private String educationId;

    @Column(name="created_at", insertable=false, updatable=false,
            columnDefinition="timestamp default current_timestamp")
    private LocalDateTime createdAt;
}