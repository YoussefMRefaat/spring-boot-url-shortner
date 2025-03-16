package com.youssef.urlshortener.dto;

import com.youssef.urlshortener.entity.Link;

public class LinkDTO {

    private String originalUrl;
    private String code;
    private boolean isActive;

    public LinkDTO(Link link) {
        this.originalUrl = link.getOriginalUrl();
        this.code = link.getCode();
        this.isActive = link.isActive();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getCode() {
        return code;
    }

    public boolean isActive() {
        return this.isActive;
    }
}
