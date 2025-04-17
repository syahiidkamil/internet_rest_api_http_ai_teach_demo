package com.syahiidkamil.internet_rest_http_ai_demo.service.impl;

import com.syahiidkamil.internet_rest_http_ai_demo.exception.CustomException;
import com.syahiidkamil.internet_rest_http_ai_demo.exception.ErrorCode;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.AiPromptRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.AiResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.service.AiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    private final WebClient geminiWebClient;
    
    @Value("${app.gemini.api-key}")
    private String apiKey;
    
    @Value("${app.gemini.model-id}")
    private String modelId;

    public AiServiceImpl(WebClient geminiWebClient) {
        this.geminiWebClient = geminiWebClient;
    }

    @Override
    public Mono<AiResponse> generateContent(AiPromptRequest request) {
        long startTime = System.currentTimeMillis();
        
        // For simplicity, we're using a non-streaming version here
        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of(
                    "role", "user",
                    "parts", List.of(
                        Map.of("text", request.getPrompt())
                    )
                )
            ),
            "generationConfig", Map.of(
                "responseMimeType", "text/plain"
            )
        );

        return geminiWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/{modelId}:generateContent")
                        .queryParam("key", apiKey)
                        .build(modelId))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    long endTime = System.currentTimeMillis();
                    double processingTime = (endTime - startTime) / 1000.0;
                    
                    // Extract text from Gemini API response
                    String responseText = "No response";
                    try {
                        // More flexible parsing that can handle different response structures
                        if (response.containsKey("candidates")) {
                            Object candidatesObj = response.get("candidates");
                            if (candidatesObj instanceof List) {
                                List<?> candidates = (List<?>) candidatesObj;
                                if (!candidates.isEmpty() && candidates.get(0) instanceof Map) {
                                    Map<?, ?> candidate = (Map<?, ?>) candidates.get(0);
                                    if (candidate.containsKey("content")) {
                                        Object contentObj = candidate.get("content");
                                        if (contentObj instanceof Map) {
                                            // Handle case where content is a Map
                                            Map<?, ?> contentMap = (Map<?, ?>) contentObj;
                                            if (contentMap.containsKey("parts")) {
                                                Object partsObj = contentMap.get("parts");
                                                if (partsObj instanceof List && !((List<?>) partsObj).isEmpty()) {
                                                    Object partObj = ((List<?>) partsObj).get(0);
                                                    if (partObj instanceof Map && ((Map<?, ?>) partObj).containsKey("text")) {
                                                        responseText = (String) ((Map<?, ?>) partObj).get("text");
                                                    }
                                                }
                                            }
                                        } else if (contentObj instanceof List && !((List<?>) contentObj).isEmpty()) {
                                            // Handle case where content is a List
                                            Object contentItem = ((List<?>) contentObj).get(0);
                                            if (contentItem instanceof Map) {
                                                Map<?, ?> contentMap = (Map<?, ?>) contentItem;
                                                if (contentMap.containsKey("parts")) {
                                                    Object partsObj = contentMap.get("parts");
                                                    if (partsObj instanceof List && !((List<?>) partsObj).isEmpty()) {
                                                        Object partObj = ((List<?>) partsObj).get(0);
                                                        if (partObj instanceof Map && ((Map<?, ?>) partObj).containsKey("text")) {
                                                            responseText = (String) ((Map<?, ?>) partObj).get("text");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        // Debugging output
                        if ("No response".equals(responseText)) {
                            System.out.println("Response structure: " + response);
                        }
                    } catch (Exception e) {
                        responseText = "Error parsing response: " + e.getMessage();
                    }
                    
                    return AiResponse.builder()
                            .response(responseText)
                            .model(modelId)
                            .processingTime(processingTime)
                            .build();
                })
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> Mono.error(new CustomException(ErrorCode.AI_SERVICE_UNAVAILABLE)));
    }
}
