package com.youssef.urlshortener.controller;

import com.youssef.urlshortener.dto.LinkDTO;
import com.youssef.urlshortener.dto.LinkListDTO;
import com.youssef.urlshortener.entity.Link;
import com.youssef.urlshortener.entity.User;
import com.youssef.urlshortener.dto.CreateLinkRequest;
import com.youssef.urlshortener.service.LinkService;
import com.youssef.urlshortener.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/links")
public class LinkController {

    private final LinkService linkService;
    private final UserService userService;

    public LinkController(LinkService linkService, UserService userService) {
        this.linkService = linkService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<LinkDTO> createShortUrl(@Valid @RequestBody CreateLinkRequest request,
                                                  @RequestHeader("Authorization") String token) {
        User user = userService.getUserFromToken(token);
        return ResponseEntity.ok(linkService.createShortUrl(request.getOriginalUrl(), user));
    }

    @GetMapping
    public ResponseEntity<LinkListDTO> getUserLinks(@RequestHeader("Authorization") String token) {
        User user = userService.getUserFromToken(token);
        return ResponseEntity.ok(linkService.getUserLinks(user));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteShortUrl(@PathVariable String code,
                                                 @RequestHeader("Authorization") String token) {
        User user = userService.getUserFromToken(token);
        linkService.deleteShortLink(code, user);
        return ResponseEntity.ok("URL deleted successfully");
    }

    @GetMapping("/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        String originalUrl = linkService.getOriginalLink(code);
        return ResponseEntity.ok(originalUrl);
    }
}