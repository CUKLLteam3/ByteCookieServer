package com.example.bytecookie.dto.recruit.save;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class SaveRecruitRequest {
    private Long userId;
    private Long sn;
}
