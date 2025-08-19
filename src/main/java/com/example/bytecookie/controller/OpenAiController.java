package com.example.bytecookie.controller;

import com.example.bytecookie.domain.openai.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openai")
public class OpenAiController {

    private final OpenAiService openAiService;

    @PostMapping("/job")
    public Map<String, String> askJob(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        String reply = openAiService.jobAi(message);
        return Map.of("reply", reply);
    }

    @PostMapping("/resume")
    public Map<String, String> askResume(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        String reply = openAiService.resumeAi(message);
        return Map.of("reply", reply);
    }

}