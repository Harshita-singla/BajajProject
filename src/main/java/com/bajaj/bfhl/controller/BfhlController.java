package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.exception.BadRequestException;
import com.bajaj.bfhl.service.AiService;
import com.bajaj.bfhl.service.BfhlService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BfhlController {

    private final BfhlService service;
    private final AiService aiService;

    private static final String EMAIL =
            "Harshita0433.be23@chitkara.edu.in";

    public BfhlController(BfhlService service, AiService aiService) {
        this.service = service;
        this.aiService = aiService;
    }

    // ✅ GET /health
    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "is_success", true,
                "official_email", EMAIL
        );
    }

    // ✅ POST /bfhl
    @PostMapping("/bfhl")
    public BfhlResponse bfhl(@RequestBody Map<String, Object> body) {

        if (body == null || body.size() != 1) {
            throw new BadRequestException("Exactly one key is required");
        }

        String key = body.keySet().iterator().next();
        Object value = body.get(key);

        switch (key) {

            case "fibonacci" -> {
                if (!(value instanceof Number))
                    throw new BadRequestException("Fibonacci value must be an integer");

                int n = ((Number) value).intValue();
                return new BfhlResponse(true, EMAIL, service.fibonacci(n));
            }

            case "prime" -> {
                if (!(value instanceof List<?>))
                    throw new BadRequestException("Prime input must be an array");

                return new BfhlResponse(true, EMAIL,
                        service.prime((List<Integer>) value));
            }

            case "hcf" -> {
                if (!(value instanceof List<?>))
                    throw new BadRequestException("HCF input must be an array");

                return new BfhlResponse(true, EMAIL,
                        service.hcf((List<Integer>) value));
            }

            case "lcm" -> {
                if (!(value instanceof List<?>))
                    throw new BadRequestException("LCM input must be an array");

                return new BfhlResponse(true, EMAIL,
                        service.lcm((List<Integer>) value));
            }

            case "AI" -> {
                if (!(value instanceof String))
                    throw new BadRequestException("AI input must be a string");

                String question = "Answer in one word only. " + value;
                String answer = aiService.askAI(question);

                if (answer == null || answer.isBlank())
                    throw new BadRequestException("AI returned empty response");

                return new BfhlResponse(true, EMAIL, answer);
            }

            default -> throw new BadRequestException("Invalid key provided");
        }
    }
}
