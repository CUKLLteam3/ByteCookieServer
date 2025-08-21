package com.example.bytecookie.domain.education.ext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HrdListResponse(
        @JsonProperty("HRDNet") HrdNet hrdNet
) {}
