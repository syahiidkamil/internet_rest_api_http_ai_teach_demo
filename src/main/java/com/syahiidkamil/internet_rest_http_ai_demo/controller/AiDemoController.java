package com.syahiidkamil.internet_rest_http_ai_demo.controller;

import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.AiPromptRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.AiResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.WebResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.service.AiService;
import com.syahiidkamil.internet_rest_http_ai_demo.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/ai")
public class AiDemoController {

    private final AiService aiService;
    private final ApiResponse apiResponse;

    public AiDemoController(AiService aiService, ApiResponse apiResponse) {
        this.aiService = aiService;
        this.apiResponse = apiResponse;
    }

    @PostMapping("/generate")
    public Mono<ResponseEntity<WebResponse<AiResponse>>> generateContent(@RequestBody AiPromptRequest request) {
        return aiService.generateContent(request)
                .map(response -> ResponseEntity.ok(apiResponse.success(response)))
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> Mono.just(
                    ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                        apiResponse.error(HttpStatus.SERVICE_UNAVAILABLE, "AI service timeout or error: " + e.getMessage())
                    )
                ));
    }

    // Secured endpoint that requires authentication
    @PostMapping("/generate-secure")
    public Mono<ResponseEntity<WebResponse<AiResponse>>> generateSecureContent(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AiPromptRequest request) {
        
        // The token validation is handled by the AuthService in a real implementation
        // Here we're just showing the concept of a secured endpoint
        
        return aiService.generateContent(request)
                .map(response -> ResponseEntity.ok(apiResponse.success(response)))
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> Mono.just(
                    ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                        apiResponse.error(HttpStatus.SERVICE_UNAVAILABLE, "AI service timeout or error: " + e.getMessage())
                    )
                ));
    }
}
