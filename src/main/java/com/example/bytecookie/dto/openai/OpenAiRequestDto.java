package com.example.bytecookie.dto.openai;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * OpenAI API 요청 DTO
 */
@Data
@AllArgsConstructor
public class OpenAiRequestDto {
    private String model;
    private List<Map<String, String>> messages;
    private double temperature;
    private int max_tokens;


}
