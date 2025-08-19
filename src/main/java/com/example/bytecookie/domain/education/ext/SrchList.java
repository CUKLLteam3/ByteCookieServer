package com.example.bytecookie.domain.education.ext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SrchList(
        @JsonProperty("scn_list") List<ScnItem> scnList
) {}
