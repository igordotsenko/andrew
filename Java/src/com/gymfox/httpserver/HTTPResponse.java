package com.gymfox.httpserver;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class HTTPResponse {
    public enum ResponseHeaders {
        HOST("Host"),
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
    private final Map<ResponseHeaders, String> headerSets;
    private final String responseBody;
    private String response;

    public static class ResponseBuilder {
        private String httpVersion;
        private String statusCode;
        private EnumMap<ResponseHeaders, String> headerSets = new EnumMap<>(ResponseHeaders.class);
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
            headerSets.put(responseHeader, responseValue);

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
        this.headerSets = responseBuilder.headerSets;
        this.responseBody = responseBuilder.responseBody;
    }

    public String buildResponse() {
                return getHTTPVersion() + " " + getStatusCode() + "\n" +
                        headerSets.entrySet()
                        .stream()
                        .map(Map.Entry::getValue)
                        .collect(Collectors.joining("\n")) + "\n" +
                        getResponseBody();
    }

    public String sendResponse() {
        return response == null ? response = buildResponse() : response;
    }

    public String getHTTPVersion() {
        return httpVersion;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getHost() {
        return headerSets.getOrDefault(ResponseHeaders.HOST, "");
    }

    public String getAllowedMethod() {
        return headerSets.getOrDefault(ResponseHeaders.ALLOWED_METHODS, "");
    }

    public String getConnection() {
        return headerSets.getOrDefault(ResponseHeaders.CONNECTION, "");
    }

    public String getContentLength() {
        return headerSets.getOrDefault(ResponseHeaders.CONTENT_LENGTH,"");
    }

    public String getContentType() {
        return headerSets.getOrDefault(ResponseHeaders.CONTENT_TYPE, "");
    }

    public String getResponseBody() {
        return Optional.ofNullable(responseBody).orElse("");
    }
}
