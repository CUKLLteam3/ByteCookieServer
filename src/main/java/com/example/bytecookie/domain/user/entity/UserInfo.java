/*
package com.example.bytecookie.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 1:1 관계 - 외래키로 user_id 참조
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;



    @Column(name = "age_group", length = 20)
    private String ageGroup;

    @Column(name = "region",length = 100)
    private String region;

    // 기타 유저 정보 필드 추가 가능
}
*/
