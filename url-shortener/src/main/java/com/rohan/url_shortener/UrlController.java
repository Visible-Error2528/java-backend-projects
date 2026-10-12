package com.rohan.url_shortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{code}")
    public ResponseEntity<UrlResponse> getByCode(@PathVariable("code") String code) {

        String originalUrl = urls.get(code);


        if (originalUrl == null){
            return ResponseEntity.notFound().build();
        }
        UrlResponse urlResponse = new UrlResponse(
                    code,
                    "http://localhost:8080/" + code,
                    originalUrl
        );
        return ResponseEntity.ok(urlResponse);

    }

}
