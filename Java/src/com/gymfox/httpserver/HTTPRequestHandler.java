package com.gymfox.httpserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.METHOD_NOT_ALLOWED;
import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.NOT_FOUND_CODE;
import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.OK_CODE;
import static com.gymfox.httpserver.HTTPResponse.ResponseHeaders.ALLOWED_METHODS;
import static com.gymfox.httpserver.HTTPResponse.ResponseHeaders.CONNECTION;
import static com.gymfox.httpserver.HTTPResponse.ResponseHeaders.CONTENT_LENGTH;
import static com.gymfox.httpserver.HTTPResponse.ResponseHeaders.CONTENT_TYPE;
import static com.gymfox.httpserver.HTTPResponse.ResponseHeaders.DATE;
import static com.gymfox.httpserver.HTTPServerExceptions.MethodIsNotAllowedException;
import static com.gymfox.httpserver.HTTPServerExceptions.NotFoundException;

public final class HTTPRequestHandler  {
    static final List<String> allowedMethodsList = Collections.unmodifiableList(Arrays.asList("GET", "POST"));
    private final HTTPServerConf httpServerConf;

    public enum CodeResponse {
        OK_CODE(200, "OK"),
        BAD_REQUEST_CODE(400, "Bad Request"),
        NOT_FOUND_CODE(404, "Not Found"),
        METHOD_NOT_ALLOWED(405, "Not allowed method");

        private String codeName;
        private int codeStatus;

        CodeResponse(int codeStatus, String codeExplain) {
            this.codeStatus = codeStatus;
            this.codeName = codeExplain;
        }

        public int getCodeStatus() {
            return codeStatus;
        }

        public String getCodeName() {
            return codeName;
        }
    }

    HTTPRequestHandler(HTTPServerConf httpServerConf) {
        this.httpServerConf = httpServerConf;
    }

    public HTTPResponse handleRequest(HTTPRequest httpRequest) throws IOException {
        HTTPResponse.ResponseBuilder responseBuilder = new HTTPResponse.ResponseBuilder();
        File requestedFile = URIUtils.processingRequestURI(httpServerConf.getRootDirectory(), httpRequest.getRequestURI());

        try {
            validateMethods(httpRequest.getRequestMethod());
            validatePath(requestedFile);

            setResponseCode(responseBuilder, OK_CODE, httpRequest.getRequestHTTPVersion());

            String body = getContentByURI(requestedFile);
            responseBuilder.addBody(body);
            responseBuilder.addHeader(CONTENT_TYPE, httpServerConf.getMimeTypes().getMimeFormat(requestedFile.getName()));
            responseBuilder.addHeader(CONTENT_LENGTH, String.valueOf(body.length()));
        } catch (NotFoundException e) {
            e.printStackTrace();

            setResponseCode(responseBuilder, NOT_FOUND_CODE, httpRequest.getRequestHTTPVersion());
        } catch (MethodIsNotAllowedException e) {
            e.printStackTrace();

            setResponseCode(responseBuilder, METHOD_NOT_ALLOWED, httpRequest.getRequestHTTPVersion());
        }

        return responseBuilder.build();
    }

    public void setResponseCode(HTTPResponse.ResponseBuilder responseBuilder, CodeResponse status, String protocol) {
        responseBuilder.addHTTPVersion(protocol);
        responseBuilder.addStatusCode(status.getCodeStatus() + " " + status.getCodeName());
        responseBuilder.addHeader(ALLOWED_METHODS, allowedMethodsList.toString());
        responseBuilder.addHeader(CONNECTION, "Closed");
        responseBuilder.addHeader(DATE, getServerCurrentTime());
    }

    private String getContentByURI(File requestedFile) throws IOException {
        return readContentByURI(requestedFile);
    }

    private String readContentByURI(File requestedFile) throws IOException {
        return String.join("\n", Files.readAllLines(Paths.get(requestedFile.getAbsolutePath())));
    }

    private String getServerCurrentTime() {
        return DateTimeFormatter.RFC_1123_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());
    }

    private void validateMethods(String method) throws MethodIsNotAllowedException {
        if ( !allowedMethodsList.contains(method) ) {
            throw new MethodIsNotAllowedException(METHOD_NOT_ALLOWED.getCodeStatus() + " " +
                    METHOD_NOT_ALLOWED.getCodeName());
        }
    }

    private void validatePath(File requestPath) throws NotFoundException {
        if (!requestPath.exists() || requestPath.isDirectory()) {
            throw new NotFoundException(NOT_FOUND_CODE.getCodeStatus() + " " + NOT_FOUND_CODE.getCodeName());
        }
    }
}
