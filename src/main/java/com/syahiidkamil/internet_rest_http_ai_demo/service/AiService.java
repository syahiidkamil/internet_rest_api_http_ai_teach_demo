package com.syahiidkamil.internet_rest_http_ai_demo.service;

import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.AiPromptRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.AiResponse;
import reactor.core.publisher.Mono;

public interface AiService {
    Mono<AiResponse> generateContent(AiPromptRequest request);
}
