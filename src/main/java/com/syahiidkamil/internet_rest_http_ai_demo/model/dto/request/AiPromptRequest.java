package com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request;

public class AiPromptRequest {
    private String prompt;

    public AiPromptRequest() {
    }

    public AiPromptRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public static AiPromptRequestBuilder builder() {
        return new AiPromptRequestBuilder();
    }

    public static class AiPromptRequestBuilder {
        private String prompt;

        AiPromptRequestBuilder() {
        }

        public AiPromptRequestBuilder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public AiPromptRequest build() {
            return new AiPromptRequest(prompt);
        }
    }
}
