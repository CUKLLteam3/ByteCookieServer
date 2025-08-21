package com.example.bytecookie.dto.recruit.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecruitmentFile {
    private long recrutAtchFileNo;
    private int sortNo;
    private String atchFileNm;
    private String atchFileType;
    private String url;
}
