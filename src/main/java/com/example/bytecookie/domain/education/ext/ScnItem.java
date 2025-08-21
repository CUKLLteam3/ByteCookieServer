package com.example.bytecookie.domain.education.ext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ScnItem(
        @JsonProperty("TRPR_ID") String trprId,
        @JsonProperty("TITLE") String title,
        @JsonProperty("TRA_START_DATE") String traStart,
        @JsonProperty("TRA_END_DATE") String traEnd,
        @JsonProperty("ADDRESS") String address,
        @JsonProperty("TRAIN_TARGET") String trainTarget,
        @JsonProperty("TEL_NO") String telNo,
        @JsonProperty("INST_CD") String instCd,   // 목록에 없을 수 있음
        @JsonProperty("INST_NM") String instNm    // 목록에 없을 수 있음
) {}
