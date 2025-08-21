package com.example.bytecookie.domain.education.ext;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor

public class HrdApiClient {

    private final RestTemplate restTemplate;

    @Value("${hrd.base-url}")
    private String baseUrl;

    @Value("${hrd.detail-url}")
    private String detailUrl;

    /**
     * 리스트 원문(JSON)으로 받음. 필터는 값 있을 때만 붙인다.
     */
    // ✅ 새 메서드: 어떤 인증키로 호출할지 외부에서 결정
    public String fetchListRawWithKey(
            String apiKey,
            String area1, String gbn, String type, String ncs1, String keyword,
            String stYmd, String enYmd,
            Integer pageNum, Integer pageSize,
            String sort, String sortCol
    ) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("HRD API key is missing");
        }

        var b = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("authKey", apiKey)
                .queryParam("returnType", "JSON")
                .queryParam("outType", "1")
                .queryParam("pageNum", pageNum == null ? 1 : pageNum)
                .queryParam("pageSize", pageSize == null ? 50 : Math.min(pageSize, 100));

        if (notBlank(area1)) b.queryParam("srchTraArea1", area1);
        if (notBlank(gbn)) b.queryParam("srchTraGbn", gbn);
        if (notBlank(type)) b.queryParam("crseTracseSe", type);
        if (notBlank(ncs1)) b.queryParam("srchNcs1", ncs1);
        if (notBlank(keyword)) b.queryParam("srchTraProcessNm", keyword);
        if (notBlank(stYmd)) b.queryParam("srchTraStDt", stYmd);
        if (notBlank(enYmd)) b.queryParam("srchTraEndDt", enYmd);
        if (notBlank(sort)) b.queryParam("sort", sort);
        if (notBlank(sortCol)) b.queryParam("sortCol", sortCol);

        String url = b.toUriString();
        try {
            ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
            if (res.getStatusCode() != HttpStatus.OK) {
                throw new IllegalStateException("Work24 " + res.getStatusCodeValue() + " : " + res.getBody());
            }
            return res.getBody();
        } catch (HttpStatusCodeException e) {
            throw new IllegalStateException("Work24 error " + e.getStatusCode().value()
                    + " body=" + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new IllegalStateException("Work24 call failed: " + url, e);
        }
    }

    private static boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }



    /**
     * 상세 원문(JSON) 호출: 필요 파라미터는 있으면 넣고, 없으면 생략
     */
    public String fetchDetailRawWithKey(
            String apiKey,
            String trprId, String trainstCstId, String type, String trprDegr
    ) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("HRD API key is missing");
        }

        var b = UriComponentsBuilder.fromHttpUrl(detailUrl)
                .queryParam("authKey", apiKey)
                .queryParam("returnType", "JSON")
                .queryParam("outType", "2");

        // 엔드포인트별 키 이름이 다를 수 있어 동의어를 같이 붙임(무시되면 자동 무시)
        if (notBlank(trprId)) {
            b.queryParam("trprId", trprId)
                    .queryParam("tracseId", trprId);
        }
        if (notBlank(trainstCstId)) {
            b.queryParam("trainstCstId", trainstCstId)
                    .queryParam("trainstCstmrId", trainstCstId);
        }
        if (notBlank(trprDegr)) {
            b.queryParam("trprDegr", trprDegr)
                    .queryParam("tracseTme", trprDegr);
        }
        if (notBlank(type)) {
            b.queryParam("crseTracseSe", type)
                    .queryParam("trainTargetCd", type);
        }

        String url = b.toUriString();
        try {
            var res = restTemplate.getForEntity(url, String.class);
            if (!res.getStatusCode().is2xxSuccessful()) {
                throw new IllegalStateException("Work24 detail " + res.getStatusCodeValue() + " : " + res.getBody());
            }
            return res.getBody();
        } catch (HttpStatusCodeException e) {
            throw new IllegalStateException("Work24 detail error " + e.getStatusCode().value()
                    + " body=" + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new IllegalStateException("Work24 detail call failed: " + url, e);
        }
    }
}