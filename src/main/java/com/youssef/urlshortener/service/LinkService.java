package com.youssef.urlshortener.service;

import com.youssef.urlshortener.dto.LinkDTO;
import com.youssef.urlshortener.dto.LinkListDTO;
import com.youssef.urlshortener.entity.Link;
import com.youssef.urlshortener.entity.User;
import com.youssef.urlshortener.exception.InvalidTokenException;
import com.youssef.urlshortener.exception.ResourceNotFoundException;
import com.youssef.urlshortener.exception.UserLimitExceededException;
import com.youssef.urlshortener.repository.LinkRepository;
import com.youssef.urlshortener.util.Base62Util;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public LinkDTO createShortUrl(String originalUrl, User user) {
        if (linkRepository.countByUser(user) >= 5) {
            throw new UserLimitExceededException("URL limit exceeded. Delete one to add more.");
        }

        String code;
        do {
            code = Base62Util.generateCode(8);
        } while (linkRepository.findByCode(code).isPresent());

        Link link = new Link();
        link.setOriginalUrl(originalUrl);
        link.setCode(code);
        link.setUser(user);
        linkRepository.save(link);
        return new LinkDTO(link);
    }

    public LinkListDTO getUserLinks(User user) {
        List<Link> links = linkRepository.findByUser(user);
        List<LinkDTO> linksList = linkRepository.findByUser(user).stream().map(LinkDTO::new).toList();
        return new LinkListDTO(linksList);
    }

    public void deleteShortLink(String code, User user) {
        Link link = linkRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("URL not found"));

        if (!link.getUser().equals(user)) {
            throw new InvalidTokenException("Unauthorized");
        }

        linkRepository.delete(link);
    }

    public String getOriginalLink(String code) {
        return linkRepository.findByCode(code)
                .map(Link::getOriginalUrl)
                .orElseThrow(() -> new ResourceNotFoundException("URL not found"));
    }
}
