package com.example.bytecookie.dto.recruit.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // 예기치 않은 필드가 있어도 무시
@Data
public class RecruitmentItem {
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
    private List<Object> files;   // 빈 배열로 보아 Object 또는 별도 DTO
    private List<Object> steps;   // 빈 배열로 보아 Object 또는 별도 DTO
}


