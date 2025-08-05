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

    /**
     * 조건 조합에 따라 유사도 점수 부여 + 중복 제거
     */
    public List<ScoredRecruitmentItem> getRecommendedRecruitments(
            String ncsCd, String regionCd, String hireTypeCd, String recrutSeCd) {

        // 중복 제거용 Map
        Map<Long, ScoredRecruitmentItem> resultMap = new LinkedHashMap<>();

        // 유사도별 조건 조합 및 점수 부여
        fetchAndMerge(resultMap, ncsCd, null, null, null, 25);
        fetchAndMerge(resultMap, ncsCd, regionCd, null, null, 50);
        fetchAndMerge(resultMap, ncsCd, regionCd, hireTypeCd, null, 75);
        fetchAndMerge(resultMap, ncsCd, regionCd, hireTypeCd, recrutSeCd, 100);

        return new ArrayList<>(resultMap.values());
    }

    private void fetchAndMerge(Map<Long, ScoredRecruitmentItem> resultMap,
                               String ncsCd, String regionCd, String hireTypeCd, String recrutSeCd,
                               int score) {
        log.info("✅ 조건 파라미터: ncsCd={}, regionCd={}, hireTypeCd={}, recrutSeCd={}", ncsCd, regionCd, hireTypeCd, recrutSeCd);

        URI url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/list")
                .queryParam("serviceKey", serviceKey)
                .queryParam("resultType", "json")
                .queryParam("numOfRows", 5)
                .queryParamIfPresent("ncsCdLst", Optional.ofNullable(ncsCd))
                .queryParamIfPresent("workRgnLst", Optional.ofNullable(regionCd))
                .queryParamIfPresent("hireTypeLst", Optional.ofNullable(hireTypeCd))
                .queryParamIfPresent("recrutSe", Optional.ofNullable(recrutSeCd))
                .build(true)
                .toUri();

        log.info("🔍 [{}% 유사도] 호출 URL: {}", score, url);

        try {
            RecruitmentListResponse response = restTemplate.getForObject(url, RecruitmentListResponse.class);

            if (response != null && response.getResult() != null) {
                for (RecruitmentItem item : response.getResult()) {
                    long key = item.getRecrutPblntSn();

                    // 이미 존재하는 경우 더 높은 점수일 때만 갱신
                    if (!resultMap.containsKey(key) || resultMap.get(key).getSimilarityScore() < score) {
                        ScoredRecruitmentItem scored = resultMap.getOrDefault(key, new ScoredRecruitmentItem());
                        copyItemData(item, scored);
                        scored.setSimilarityScore(score);
                        resultMap.put(key, scored);
                    }
                }
            }
        } catch (Exception e) {
            log.error("❌ API 호출 중 오류 발생: {}", e.getMessage());
        }
    }

    /**
     * DTO 복사 유틸
     */
    private void copyItemData(RecruitmentItem source, ScoredRecruitmentItem target) {
        target.setRecrutPblntSn(source.getRecrutPblntSn());
        target.setInstNm(source.getInstNm());
        target.setRecrutPbancTtl(source.getRecrutPbancTtl());
        target.setNcsCdLst(source.getNcsCdLst());
        target.setHireTypeLst(source.getHireTypeLst());
        target.setWorkRgnLst(source.getWorkRgnLst());
        target.setRecrutSe(source.getRecrutSe());
        target.setPbancEndYmd(source.getPbancEndYmd());
        target.setSrcUrl(source.getSrcUrl());
    }



}
