package com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response;

public class WebResponse<T> {
    private Integer code;
    private String status;
    private String message;
    private T data;

    public WebResponse() {
    }

    public WebResponse(Integer code, String status, String message, T data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> WebResponseBuilder<T> builder() {
        return new WebResponseBuilder<>();
    }

    public static class WebResponseBuilder<T> {
        private Integer code;
        private String status;
        private String message;
        private T data;

        WebResponseBuilder() {
        }

        public WebResponseBuilder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public WebResponseBuilder<T> status(String status) {
            this.status = status;
            return this;
        }

        public WebResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public WebResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public WebResponse<T> build() {
            return new WebResponse<>(this.code, this.status, this.message, this.data);
        }
    }
}
