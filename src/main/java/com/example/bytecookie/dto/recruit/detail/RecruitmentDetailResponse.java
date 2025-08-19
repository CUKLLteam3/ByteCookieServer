package com.example.bytecookie.dto.recruit.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecruitmentDetailResponse {
    private long recrutPblntSn;
    private String pblntInstCd;
    private String pbadmsStdInstCd;
    private String instNm;
    private String ncsCdLst;
    private String ncsCdNmLst;
    private String hireTypeLst;
    private String hireTypeNmLst;
    private String workRgnLst;
    private String workRgnNmLst;
    private String recrutSe;
    private String recrutSeNm;
    private String prefCondCn;
    private int recrutNope;
    private String pbancBgngYmd;
    private String pbancEndYmd;
    private String recrutPbancTtl;
    private String srcUrl;
    private String replmprYn;
    private String aplyQlfcCn;
    private String disqlfcRsn;
    private String scrnprcdrMthdExpln;
    private String prefCn;
    private String acbgCondLst;
    private String acbgCondNmLst;
    private String nonatchRsn;
    private String ongoingYn;
    private int decimalDay;
    private List<RecruitmentFile> files;  // 파일 DTO
    private List<RecruitmentStep> steps;  // 단계 DTO
}

