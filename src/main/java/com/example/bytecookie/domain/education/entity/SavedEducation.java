package com.example.bytecookie.domain.education.entity;


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

    @Column(name = "user_id", nullable = false)
    private Long userId;  // ← FK 대신 그냥 userId 필드만 사용

    @Column(name="education_id", nullable=false, length=100)
    private String educationId;

    @Column(name="created_at", insertable=false, updatable=false,
            columnDefinition="timestamp default current_timestamp")
    private LocalDateTime createdAt;
}