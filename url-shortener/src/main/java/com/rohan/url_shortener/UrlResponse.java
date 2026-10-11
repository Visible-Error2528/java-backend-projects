package com.rohan.url_shortener;

public record UrlResponse(String shortCode, String shortUrl, String originalUrl) {
}
