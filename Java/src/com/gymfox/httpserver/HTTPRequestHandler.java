package com.gymfox.httpserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.gymfox.httpserver.HTTPResponse.ResponseHeaders.*;
import static com.gymfox.httpserver.HTTPServer.mimeTypeFile;

public final class HTTPRequestHandler {
    private static final List<String> allowedMethodsList = Arrays.asList("GET", "POST");

    public enum CodeResponse {
        OK_CODE(200, "OK"),
        NOT_FOUND_CODE(404, "Not Found"),
        BAD_REQUEST_CODE(405, "Bad Request");

        private String codeExplain;
        private int codeStatus;

        CodeResponse(int codeStatus, String codeExplain) {
            this.codeStatus = codeStatus;
            this.codeExplain = codeExplain;
        }

        public int getCodeStatus() {
            return codeStatus;
        }

        public String getCodeExplain() {
            return codeExplain;
        }
    }

    HTTPRequestHandler() {}

    public HTTPResponse handleRequest(HTTPRequest httpRequest) throws IOException {
        HTTPResponse.ResponseBuilder responseBuilder = new HTTPResponse.ResponseBuilder();
        File fullRequestPath = URIHandler.processingRequestURI(httpRequest.getConfig().getRootDirectory(), httpRequest.getRequestURI());
        CodeResponse status = checkRequestParameters(httpRequest, fullRequestPath);

        responseBuilder.addHTTPVersion(httpRequest.getRequestHTTPVersion());
        responseBuilder.addStatusCode(status.getCodeStatus() + " " + status.getCodeExplain());

        responseBuilder.addHeader(HOST, HOST.getHeaderName() + ": " + httpRequest.getHost());
        responseBuilder.addHeader(ALLOWED_METHODS, ALLOWED_METHODS.getHeaderName() + ": " + String.valueOf(allowedMethodsList));
        responseBuilder.addHeader(CONNECTION, CONNECTION.getHeaderName() + ": " + "Closed");

        if ( !isBadRequestStatus(status) ) {
            responseBuilder.addHeader(CONTENT_TYPE, CONTENT_TYPE.getHeaderName() + ": "
                    + mimeTypeFile.getMimeFormat(String.valueOf(fullRequestPath)));
        }
        if ( isOkStatus(status)) {
            String body = getContentByURI(status, fullRequestPath);
            responseBuilder.addBody(body);
            responseBuilder.addHeader(CONTENT_LENGTH, CONTENT_LENGTH.getHeaderName() + ": " + String.valueOf(body.length()));
        }
        responseBuilder.addHeader(DATE, DATE.getHeaderName() + ": " + getServerCurrentTime());

        return responseBuilder.build();
    }

    public String getContentByURI(CodeResponse status, File fullRequestPath) throws IOException {
        return isOkStatus(status) ? readContentByURI(fullRequestPath) : "";
    }

    public String readContentByURI(File fullRequestPath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(String.valueOf(fullRequestPath))));
    }

    public String getServerCurrentTime() {
        return DateTimeFormatter.RFC_1123_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());
    }

    public CodeResponse checkRequestParameters(HTTPRequest request, File requestPath) {
        if ( !allowedMethodsList.contains(request.getRequestMethod()) ) {
            return CodeResponse.BAD_REQUEST_CODE;
        }

        if ( !requestPath.exists() ) {
            return CodeResponse.NOT_FOUND_CODE;
        }

        return CodeResponse.OK_CODE;
    }

    public boolean isOkStatus(CodeResponse status) {
        return status == CodeResponse.OK_CODE;
    }

    public boolean isBadRequestStatus(CodeResponse status) {
        return status == CodeResponse.BAD_REQUEST_CODE;
    }
}
