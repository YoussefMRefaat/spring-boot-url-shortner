package com.youssef.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateLinkRequest {
    @NotBlank(message = "Original URL is required")
    @Pattern(
            regexp = "^(https?://)?([\\w.-]+)?([a-zA-Z0-9]{1,63}\\.[a-zA-Z]{2,6})([/\\w.-]*)*\\??([^#]*)#?(.*)$",
            message = "Invalid url format"
    )
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}