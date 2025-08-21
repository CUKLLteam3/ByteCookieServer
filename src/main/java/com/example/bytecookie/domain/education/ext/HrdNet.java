package com.example.bytecookie.domain.education.ext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HrdNet(
        @JsonProperty("scn_cnt") String scnCnt,
        @JsonProperty("pageNum") String pageNum,
        @JsonProperty("pageSize") String pageSize,
        @JsonProperty("srchList") SrchList srchList
) {}

