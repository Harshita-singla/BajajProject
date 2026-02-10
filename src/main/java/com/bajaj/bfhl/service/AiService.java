package com.bajaj.bfhl.service;

import com.bajaj.bfhl.exception.AiServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;
@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";

    public String askAI(String question) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> body = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "role", "user",
                                    "parts", List.of(
                                            Map.of("text", question)
                                    )
                            )
                    )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(GEMINI_URL + apiKey, request, Map.class);

            Map responseBody = response.getBody();
            if (responseBody == null || !responseBody.containsKey("candidates")) {
                return "Unknown";
            }

            List candidates = (List) responseBody.get("candidates");
            if (candidates.isEmpty()) {
                return "Unknown";
            }

            Map candidate = (Map) candidates.get(0);
            Map content = (Map) candidate.get("content");
            List parts = (List) content.get("parts");

            if (parts == null || parts.isEmpty()) {
                return "Unknown";
            }

            String answer = parts.get(0).toString();
            return answer.split("\\s+")[0];

        } catch (Exception e) {
            throw new AiServiceException();
        }
    }
}
