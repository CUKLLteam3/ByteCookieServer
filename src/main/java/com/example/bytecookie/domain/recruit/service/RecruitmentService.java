package com.example.bytecookie.domain.recruit.service;


import com.example.bytecookie.dto.recruit.detail.RecruitmentDetailWrapperResponse;
import com.example.bytecookie.dto.recruit.list.RecruitmentSearchRequest;
import com.example.bytecookie.dto.recruit.list.ScoredRecruitmentItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import com.example.bytecookie.dto.recruit.detail.RecruitmentDetailResponse;
import com.example.bytecookie.dto.recruit.list.RecruitmentItem;
import com.example.bytecookie.dto.recruit.list.RecruitmentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

/**
 * 공공데이터포털 API를 활용한 채용 정보 조회 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final String baseUrl = "https://apis.data.go.kr/1051000/recruitment";

    @Value("${recruit.api.key}")
    private String serviceKey;

    RestTemplate restTemplate = new RestTemplate();


    /**
     * 채용 공고 목록 조회
     */
    public List<RecruitmentItem> getRecruitmentList() {
        URI url = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/list")
                .queryParam("serviceKey", serviceKey)
                .queryParam("resultType", "json")
                .queryParam("numOfRows", 10)
                .build(true)
                .toUri();

        System.out.println("🛰️ 호출한 공고 목록 조회 URL: " + url);

        ResponseEntity<RecruitmentListResponse> response = restTemplate.getForEntity(url, RecruitmentListResponse.class);

        RecruitmentListResponse body = response.getBody();

        return body.getResult();

    }


    /**
     * 채용 상세 정보 조회
     */

    public RecruitmentDetailResponse getRecruitmentDetail(Long sn) {

        URI url = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/detail")
                .queryParam("serviceKey", serviceKey)
                .queryParam("resultType", "json")
                .queryParam("sn", sn)
                .build(true)
                .toUri();

        System.out.println("🛰️ 호출한 상세 정보 조회 URL: " + url);
        ResponseEntity<RecruitmentDetailWrapperResponse> response = restTemplate.getForEntity(url, RecruitmentDetailWrapperResponse.class);

        RecruitmentDetailResponse detail = response.getBody().getResult();
        System.out.println("공고명: " + detail.getRecrutPbancTtl());
        return detail;
    }

    /**
     * 원하는 분야 채용 공고 목록 조회
     */
    public List<RecruitmentItem> getRecruitmentwantList(RecruitmentSearchRequest req) {
        URI url = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/list")
                .queryParam("serviceKey", serviceKey)
                .queryParamIfPresent("ncsCdLst", Optional.ofNullable(req.getNcsCdLst()))
                .queryParamIfPresent("hireTypeLst", Optional.ofNullable(req.getHireTypeLst()))
                .queryParamIfPresent("workRgnLst", Optional.ofNullable(req.getWorkRgnLst()))
                .queryParamIfPresent("recrutSe", Optional.ofNullable(req.getRecrutSe()))
                .queryParam("resultType", "json")
                .queryParam("numOfRows", 10)
                .build(true)
                .toUri();

        System.out.println("🛰️ 호출한 공고 목록 조회 URL: " + url);

        ResponseEntity<RecruitmentListResponse> response = restTemplate.getForEntity(url, RecruitmentListResponse.class);

        RecruitmentListResponse body = response.getBody();

        return body.getResult();

    }





}
