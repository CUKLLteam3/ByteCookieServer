package com.example.bytecookie.domain.recruit.entity;


import com.example.bytecookie.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "saved_recruit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SavedRecruit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;// ✅ 유저 ID

    private Long sn;      // ✅ 저장한 공고문 sn



    // @Getter, @Setter or Lombok @Data
}
