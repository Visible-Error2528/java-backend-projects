package com.rohan.url_shortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final Map<String,String> urls = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<UrlResponse> create(@RequestBody CreateUrlRequest request) {

        if (request.url() == null || request.url().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        String shortCode = UUID.randomUUID().toString().substring(0,6);

        String shortUrl = "http://localhost:8080/" + shortCode;

        urls.put(shortCode, request.url());

        UrlResponse urlResponse = new UrlResponse(
                shortCode,
                shortUrl,
                request.url()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(urlResponse);

    }
}
