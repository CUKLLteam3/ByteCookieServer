package com.example.bytecookie.domain.openai;

import com.example.bytecookie.dto.openai.OpenAiRequestDto;
import com.example.bytecookie.dto.openai.OpenAiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;

@Service // ✅ 이 클래스가 Spring의 서비스(비즈니스 로직 담당)임을 나타냄
@RequiredArgsConstructor
public class OpenAiService {

    // ✅ 환경변수에서 API 키를 읽어옴 (application.yml → 환경변수 연결)
    @Value("${openai.api-key}")
    private String apiKey;

    // ✅ 애플리케이션이 실행되면 가장 먼저 API 키가 잘 들어왔는지 확인
    @PostConstruct
    public void checkKey() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("OPENAI_API_KEY 환경변수가 설정되지 않았습니다.");
        }
    }

    /**
     * ✅ 실제로 OpenAI에게 메시지를 전달하고 응답을 받아오는 메서드
     * @param userMessage 사용자가 보내는 메시지
     * @return ChatGPT의 응답 텍스트
     */
    // 1. 메시지 리스트 생성
    List<Map<String, String>> messages = new ArrayList<>();
    String severPromt = new String();

    public String jobAi(String userMessage) {
        severPromt = "뒤에 올 내용을 바탕으로 이 사람한테 어울리는 직업을 추천해줘. 추천할 직업명만 딱 주면 되.";
        return askChatGPT(userMessage, severPromt);
    }
    public String resumeAi(String userMessage) {
        severPromt = "뒤에 올 이력서에서 수정할 부분을 알려줘 한 3가지 정도면 충분해. 출력할 때 저는 이 부분을 이렇게 바꾸길 추천드립니다. 이런식으로! ";
        return askChatGPT(userMessage, severPromt);
    }






    public String askChatGPT(String userMessage, String severPromt) {


        // 2. system 메시지 추가
        messages.add(Map.of(
                "role", "system",
                "content", severPromt
        ));

        // 3. user 메시지 추가
        messages.add(Map.of(
                "role", "user",
                "content", userMessage
        ));

        // ✅ 1. OpenAI Chat API 주소 (정해진 형식)
        String url = "https://api.openai.com/v1/chat/completions";

        // ✅ 2. 요청 메시지 구성 (OpenAI가 요구하는 구조)
        OpenAiRequestDto request = new OpenAiRequestDto(
                "gpt-3.5-turbo",  // 사용 모델 이름: gpt-3.5-turbo 또는 gpt-4o
                //List.of(Map.of("role", "user", "content", userMessage)), // ✅ 필수 포맷: role + content
                messages,
                0.7, // 생성 다양성 조절 (0 ~ 1, 높을수록 창의적)
                400
        );


        // ✅ 3. HTTP 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // JSON 전송
        headers.setBearerAuth(apiKey); // ✅ 인증 토큰 (sk-... 형식의 API 키)

        // ✅ 4. 요청 본문(Entity) 만들기
        HttpEntity<OpenAiRequestDto> entity = new HttpEntity<>(request, headers);

        //플로우 분리

        // ✅ 5. 실제 HTTP 요청을 보내는 객체 (Spring 제공)
        RestTemplate restTemplate = new RestTemplate();

        // ✅ 6. OpenAI API에 POST 요청 보내고 응답을 DTO로 파싱
        ResponseEntity<OpenAiResponseDto> response = restTemplate.exchange(
                url,                 // 요청할 URL
                HttpMethod.POST,    // HTTP 메서드
                entity,             // 요청 본문 (DTO + 헤더)
                OpenAiResponseDto.class // 응답 받을 타입
        );

        // ✅ 7. 응답 중에서 가장 첫 번째 메시지 내용을 추출해서 반환
        return response.getBody()             // 전체 응답 객체
                .getChoices()          // 응답 목록
                .get(0)                // 첫 번째 응답
                .getMessage()          // 메시지 내용
                .getContent();         // 실제 텍스트
    }
}
