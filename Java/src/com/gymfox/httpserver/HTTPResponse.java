package com.gymfox.httpserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class HTTPResponse {
    public enum ResponseHeaders {
        ALLOWED_METHODS("Allowed"),
        CONNECTION("Connection"),
        CONTENT_LENGTH("Content-length"),
        CONTENT_TYPE("Content-type"),
        DATE("Date");

        String headerName;

        ResponseHeaders(String headerName) {
            this.headerName = headerName;
        }

        public String getHeaderName() {
            return headerName;
        }
    }

    private final String httpVersion;
    private final String statusCode;
    private final Map<ResponseHeaders, String> headers;
    private final String responseBody;
    private String response;

    public static class ResponseBuilder {
        private String httpVersion;
        private String statusCode;
        private Map<ResponseHeaders, String> headers = new HashMap<>();
        private String responseBody;

        public ResponseBuilder() {}

        public ResponseBuilder addHTTPVersion(String httpVersion) {
            this.httpVersion =  httpVersion;

            return self();
        }

        public ResponseBuilder addStatusCode(String statusCode) {
            this.statusCode = statusCode;

            return self();
        }

        public ResponseBuilder addHeader(ResponseHeaders responseHeader, String responseValue) {
            headers.put(responseHeader, responseHeader.getHeaderName() + ": " + responseValue);

            return self();
        }

        public ResponseBuilder addBody(String responseBody) {
            this.responseBody = responseBody;

            return self();
        }

        private ResponseBuilder self() {
            return this;
        }

        public HTTPResponse build() {
            return new HTTPResponse(this);
        }
    }

    private HTTPResponse(ResponseBuilder responseBuilder) {
        this.httpVersion = responseBuilder.httpVersion;
        this.statusCode = responseBuilder.statusCode;
        this.headers = responseBuilder.headers;
        this.responseBody = responseBuilder.responseBody;
    }

    public String buildResponse() {
                return getHTTPVersion().map(httpVersion -> httpVersion + " ").orElse("") + getStatusCode() + "\n" +
                        buildHeaders() +
                        getResponseBody().map(lines -> "\n" + lines).orElse("");
    }

    private String buildHeaders() {
        return String.join("\n", headers.values());
    }

    public String getFullResponse() {
        return response == null ? response = buildResponse() : response;
    }

    public Optional<String> getHeader(ResponseHeaders headerName) {
        return Optional.ofNullable(headers)
                .map(header -> header.get(headerName));
    }

    public Optional<String> getHTTPVersion() {
        return Optional.ofNullable(httpVersion);
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Optional<String> getResponseBody() {
        return Optional.ofNullable(responseBody);
    }
}
