package com.bajaj.bfhl.exception;

import com.bajaj.bfhl.dto.BfhlResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String EMAIL =
            "Harshita0433.be23@chitkara.edu.in";

    // 400 - Invalid input
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BfhlResponse> handleBadRequest(BadRequestException e) {
        return ResponseEntity
                .badRequest()
                .body(new BfhlResponse(false, EMAIL, e.getMessage()));
    }

    // 502 - AI service failure
    @ExceptionHandler(AiServiceException.class)
    public ResponseEntity<BfhlResponse> handleAiError(AiServiceException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(new BfhlResponse(false, EMAIL, "AI service unavailable"));
    }

    // 500 - Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlResponse> handleServerError(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BfhlResponse(false, EMAIL, "Internal server error"));
    }
}
