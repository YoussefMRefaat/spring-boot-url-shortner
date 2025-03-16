package com.youssef.urlshortener.dto;

import com.youssef.urlshortener.entity.Link;

import java.util.List;

public class LinkListDTO {
    private List<LinkDTO> links;

    public LinkListDTO(List<LinkDTO> links) {
        this.links = links;
    }

    public List<LinkDTO> getLinks() {
        return this.links;
    }
}
