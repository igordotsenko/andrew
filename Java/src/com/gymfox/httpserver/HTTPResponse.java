package com.gymfox.httpserver;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

final class HTTPResponse {
    private static String RESPONSE_CODE_OK = "OK 200";
    private String httpVersion;
    private String contentType;
    private String response;
    private long contentSize;
    private String content;

    HTTPResponse(String requestHTTPVersion, long httpContentSize, String httpContentType, String httpContent) {
        this.httpVersion = requestHTTPVersion;
        this.contentSize = httpContentSize;
        this.contentType = httpContentType;
        this.content = httpContent;
    }

    void createResponse() {
        response = "Response:\n\t" +
                getHttpVersion() + " " + RESPONSE_CODE_OK + "\n\t" +
                "Connection: close" + "\r\n\t" +
                "Content-length: " + getContentSize() + "\r\n\t" +
                "Content-type: " + getContentType() + "\r\n\t" +
                "Date: " + getServerCurrentTime() + "\r\n" +
                getContent();
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getContentType() {
        return contentType;
    }

    public long getContentSize() {
        return contentSize;
    }

    public String getServerCurrentTime() {
        return DateTimeFormatter.RFC_1123_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());
    }

    public String getContent() {
        return content;
    }

    String getResponse() {
        return response;
    }
}
