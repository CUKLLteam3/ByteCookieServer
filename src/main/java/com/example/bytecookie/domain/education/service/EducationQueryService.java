package com.example.bytecookie.domain.education.service;

import com.example.bytecookie.domain.education.ext.HrdApiClient;
import com.example.bytecookie.dto.education.EducationDetailDto;
import com.example.bytecookie.dto.education.EducationListItemDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationQueryService {

    private final HrdApiClient hrd;
    private static final ObjectMapper M = new ObjectMapper();
    private static final Set<String> AREA_WHITELIST =
            new HashSet<>(Arrays.asList("11", "41", "28"));

    // 환경변수에서 주입

    private String keyCard = "7ec31f6c-6456-4b16-ae82-6d802051bdd5";
    private String keyApprentice = "f39accaa-be41-4c63-b5f1-9aea59af47a2";


    /**
     * 목록 조회: 두 키(card+apprentice) 병합이 기본
     */
    public List<EducationListItemDto> list(
            String area1Csv, String gbn, String type, String ncs1, String keyword,
            String sort, String sortCol, Integer page, Integer size, String stYmd, String enYmd
    ) {
        List<String> apiKeys = Arrays.asList(keyCard, keyApprentice)
                .stream().filter(k -> k != null && !k.isBlank())
                .collect(Collectors.toList());
        if (apiKeys.isEmpty()) throw new IllegalStateException("HRD API keys are not set");

        List<String> areas = parseAreasOrDefault(area1Csv);
        Map<String, EducationListItemDto> dedup = new LinkedHashMap<>();

        for (String apiKey : apiKeys) {
            for (String a : areas) {
                String raw = hrd.fetchListRawWithKey(apiKey, a, gbn, type, ncs1, keyword,
                        stYmd, enYmd, page, size, sort, sortCol);
                List<JsonNode> items = extractArrayNodes(raw);

                for (JsonNode n : items) {
                    String id = text(n, "trprId");
                    if (id == null || id.isBlank()) continue;

                    EducationListItemDto dto = EducationListItemDto.builder()
                            .id(id)
                            .title(nz(text(n, "title")))
                            .startDate(nz(text(n, "traStartDate"))) // yyyy-MM-dd
                            .endDate(nz(text(n, "traEndDate")))
                            .address(nz(text(n, "address")))
                            .trainTarget(nz(text(n, "trainTarget"))) // 훈련유형 이름
                            .build();

                    // trprId 기준 중복 제거
                    dedup.putIfAbsent(id, dto);
                }
            }
        }

        List<EducationListItemDto> list = new ArrayList<>(dedup.values());
        list.sort(makeComparator(sort));                 // 서버 정렬 유지
        if (list.size() > 50) return list.subList(0, 50); // 안전상 컷
        return list;
    }

    /**
     * 상세 조회: 기본 both → id만으로 시도 → 필요 시 목록에서 보조 파라미터 추출 후 재시도
     */
    /** 상세조회 JSON → DTO 변환 */
    private static EducationDetailDto buildDetailDto(String id, JsonNode n) {
        return EducationDetailDto.builder()
                .id(id)
                .title(nz(text(n, "title")))
                .subTitle(nz(text(n, "subTitle")))
                .startDate(nz(text(n, "traStartDate")))
                .endDate(nz(text(n, "traEndDate")))
                .address(nz(text(n, "address")))
                .regCourseMan(asInt(text(n, "regCourseMan")))
                .yardMan(asInt(text(n, "yardMan")))
                .courseFee(asInt(text(n, "courseMan")))
                .trainTarget(nz(text(n, "trainTarget")))
                .trainTargetCd(nz(text(n, "trainTargetCd")))
                .providerName(nz(text(n, "instNm"), nz(text(n, "subTitle"))))
                .managerName(nz(text(n, "managerNm")))
                .telNo(nz(text(n, "telNo")))
                .homepage(nz(text(n, "homepage"), "https://hrd.work24.go.kr/"))
                .build();
    }

    /** 목록 JSON → 간이 상세 DTO */
    private static EducationDetailDto buildDetailDtoFromList(JsonNode n) {
        return EducationDetailDto.builder()
                .id(nz(text(n, "trprId")))
                .title(nz(text(n, "title")))
                .subTitle(nz(text(n, "subTitle")))
                .startDate(nz(text(n, "traStartDate")))
                .endDate(nz(text(n, "traEndDate")))
                .address(nz(text(n, "address")))
                .regCourseMan(asInt(text(n, "regCourseMan")))
                .yardMan(asInt(text(n, "yardMan")))
                .courseFee(asInt(text(n, "courseMan")))
                .trainTarget(nz(text(n, "trainTarget")))
                .trainTargetCd(nz(text(n, "trainTargetCd")))
                .providerName(nz(text(n, "subTitle")))
                .telNo(nz(text(n, "telNo")))
                .homepage("https://hrd.work24.go.kr/")
                .build();
    }
    public EducationDetailDto detail(String id, String program) {
        String prog = (program == null || program.isBlank()) ? "both" : program.toLowerCase(Locale.ROOT);
        List<String> keys = "card".equals(prog) ? Collections.singletonList(keyCard)
                : "apprentice".equals(prog) ? Collections.singletonList(keyApprentice)
                : Arrays.asList(keyCard, keyApprentice);
        keys = keys.stream().filter(k -> k != null && !k.isBlank()).collect(Collectors.toList());
        if (keys.isEmpty()) throw new IllegalStateException("HRD API keys are not set");

        // 1) trprId만으로 상세 시도
        for (String key : keys) {
            try {
                String raw = hrd.fetchDetailRawWithKey(key, id, null, null, null);
                JsonNode n = parseDetailNode(raw);
                if (n != null) return buildDetailDto(id, n);
            } catch (Exception e) {
                // 실패 무시 → 다음 루트 진행
            }
        }

        // 2) 목록에서 보조 파라미터 찾아 재시도
        JsonNode listNode = findListItemByIdFromAllAreas(keys, id);
        if (listNode != null) {
            String trainstCstId = firstNonBlank(text(listNode, "trainstCstId"), text(listNode, "trainstCstmrId"));
            String trprDegr     = firstNonBlank(text(listNode, "trprDegr"), text(listNode, "tracseTme"));
            String type         = firstNonBlank(text(listNode, "trainTargetCd"), text(listNode, "crseTracseSe"));

            for (String key : keys) {
                try {
                    String raw = hrd.fetchDetailRawWithKey(key, id, trainstCstId, type, trprDegr);
                    JsonNode n = parseDetailNode(raw);
                    if (n != null) return buildDetailDto(id, n);
                } catch (Exception e) {
                    // 실패 무시 → 다음 키로
                }
            }

            // 그래도 안되면 목록 기반 간이 상세 반환
            return buildDetailDtoFromList(listNode);
        }

        throw new NoSuchElementException("education not found: " + id);
    }


    /* ----------------- JSON 파서/유틸 ----------------- */

    private static List<JsonNode> extractArrayNodes(String raw) {
        try {
            JsonNode root = M.readTree(raw);
            List<String> paths = Arrays.asList(
                    "/srchList",
                    "/HRDNet/srchList/scn_list",
                    "/HRDNet/svcList/svc_list",
                    "/HRDNet/data/list",
                    "/list"
            );
            for (String p : paths) {
                JsonNode arr = root.at(p);
                if (arr.isArray() && arr.size() > 0) return toList(arr);
            }
            return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private static JsonNode parseDetailNode(String raw) {
        try {
            JsonNode root = M.readTree(raw);
            List<String> paths = Arrays.asList("/data", "/detail", "/HRDNet/data", "/HRDNet/detail", "/result");
            for (String p : paths) {
                JsonNode node = root.at(p);
                if (node != null && !node.isMissingNode() && node.isObject()) return node;
            }
            return root.isObject() ? root : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static List<JsonNode> toList(JsonNode arr) {
        List<JsonNode> out = new ArrayList<>();
        arr.forEach(out::add);
        return out;
    }

    private static String text(JsonNode n, String k) {
        return (n != null && n.hasNonNull(k)) ? n.get(k).asText() : null;
    }

    private static String nz(String s) {
        return (s == null || s.isBlank()) ? "" : s;
    }

    private static String nz(String s, String def) {
        return (s == null || s.isBlank()) ? def : s;
    }

    private static Integer asInt(String s) {
        try {
            if (s == null || s.isBlank()) return null;
            String digits = s.replaceAll("[^0-9]", "");
            if (digits.isEmpty()) return null;
            return Integer.valueOf(digits);
        } catch (Exception e) {
            return null;
        }
    }

    private static List<String> parseAreasOrDefault(String csv) {
        if (csv == null || csv.isBlank()) return Arrays.asList("11", "41", "28");
        List<String> out = new ArrayList<>();
        for (String p : csv.split(",")) {
            String v = p.trim();
            if (AREA_WHITELIST.contains(v)) out.add(v);
        }
        return out.isEmpty() ? Arrays.asList("11", "41", "28") : out;
    }

    private static Comparator<EducationListItemDto> makeComparator(String sort) {
        Comparator<EducationListItemDto> byStart =
                Comparator.comparing(EducationListItemDto::getStartDate, Comparator.nullsLast(String::compareTo));
        Comparator<EducationListItemDto> byEnd =
                Comparator.comparing(EducationListItemDto::getEndDate, Comparator.nullsLast(String::compareTo));
        if ("startDateDesc".equals(sort)) return byStart.reversed();
        if ("endDateAsc".equals(sort)) return byEnd;
        if ("endDateDesc".equals(sort)) return byEnd.reversed();
        return byStart; // startDateAsc (default)
    }

    /**
     * 두 키 × (서울/경기/인천)에서 id 일치 항목 검색
     */

    private JsonNode findListItemByIdFromAllAreas(List<String> keys, String id) {
        List<String> areas = Arrays.asList("11", "41", "28");
        for (String key : keys) {
            for (String area : areas) {
                try {
                    String raw = hrd.fetchListRawWithKey(key, area, null, null, null, null,
                            null, null, 1, 100, null, null);
                    for (JsonNode n : extractArrayNodes(raw)) {
                        if (id.equals(text(n, "trprId"))) return n;
                    }
                } catch (Exception e) {
                    // 실패 무시하고 다음 area 시도
                }
            }
        }
        return null;
    }

    private static String firstNonBlank(String a, String b) {
        return (a != null && !a.isBlank()) ? a : b;
    }
}