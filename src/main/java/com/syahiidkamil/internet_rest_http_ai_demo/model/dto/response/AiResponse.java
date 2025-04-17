package com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response;

public class AiResponse {
    private String response;
    private String model;
    private double processingTime;

    public AiResponse() {
    }

    public AiResponse(String response, String model, double processingTime) {
        this.response = response;
        this.model = model;
        this.processingTime = processingTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(double processingTime) {
        this.processingTime = processingTime;
    }

    public static AiResponseBuilder builder() {
        return new AiResponseBuilder();
    }

    public static class AiResponseBuilder {
        private String response;
        private String model;
        private double processingTime;

        AiResponseBuilder() {
        }

        public AiResponseBuilder response(String response) {
            this.response = response;
            return this;
        }

        public AiResponseBuilder model(String model) {
            this.model = model;
            return this;
        }

        public AiResponseBuilder processingTime(double processingTime) {
            this.processingTime = processingTime;
            return this;
        }

        public AiResponse build() {
            return new AiResponse(response, model, processingTime);
        }
    }
}
