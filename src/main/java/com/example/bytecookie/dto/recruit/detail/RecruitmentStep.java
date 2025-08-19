package com.example.bytecookie.dto.recruit.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecruitmentStep {
    private long recrutStepSn;
    private long recrutPblntSn;
    private String recrutPbancTtl;
    private Integer recrutNope;
    private Integer aplyNope;
    private Integer cmpttRt;
    private String rsnOcrnYmd;
    private int sortNo;
    private long minStepSn;
    private long maxStepSn;
}

