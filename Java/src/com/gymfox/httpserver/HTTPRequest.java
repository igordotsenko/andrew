package com.gymfox.httpserver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gymfox.httpserver.HTTPServerExceptions.InvalidHTTPVersionException;
import static com.gymfox.httpserver.HTTPServerExceptions.InvalidPartsHTTPVersionException;

public final class HTTPRequest {
    private static final List<Double> VALID_HTTP_VERSIONS = Collections.unmodifiableList(Arrays.asList(0.9, 1.0, 1.1, 2.0));
    private static final int FIRST_PART = 0;
    private static final int SECOND_PART = 1;
    private static final int PROTOCOL_PARTS = 2;
    private static final String PROTOCOL_NAME = "HTTP";
    private final String method;
    private final String uri;
    private final String protocolVersion;
    private final String host;
    private String requestStringRepresentation;

    public HTTPRequest(String method, String inputURI, String protocolVersion, String host) throws IOException {
        this.method = method.toUpperCase();
        this.uri = inputURI;
        this.protocolVersion = checkHTTPVersion(protocolVersion);
        this.host = host;
    }

    static String checkHTTPVersion(String requestHttpVersion) throws InvalidHTTPVersionException,
            InvalidPartsHTTPVersionException {
        String newRequestHttpVersion = requestHttpVersion.toUpperCase();
        validateRequestHttpVersion(newRequestHttpVersion);

        return newRequestHttpVersion;
    }

    static void validateRequestHttpVersion(String requestHttpVersion) throws InvalidHTTPVersionException, InvalidPartsHTTPVersionException {
        String[] httpVersionParts = requestHttpVersion.split("/");
        validateParts(httpVersionParts);

        double httpVersion = Double.parseDouble(httpVersionParts[SECOND_PART]);

        validateProtocolName(httpVersionParts[FIRST_PART]);
        validateProtocolVersion(httpVersion);
    }

    private static void validateParts(String[] httpVersionParts) throws InvalidPartsHTTPVersionException {
        if ( httpVersionParts.length != PROTOCOL_PARTS) {
            throw new InvalidPartsHTTPVersionException(String.format("Invalid HTTP version parts. Expected %d, but found %d",
                    PROTOCOL_PARTS, httpVersionParts.length));
        }
    }

    private static void validateProtocolName(String httpName) throws InvalidHTTPVersionException {
        if ( !httpName.equals(PROTOCOL_NAME) ) {
            throw new InvalidHTTPVersionException("Invalid protocol name");
        }
    }

    private static void validateProtocolVersion(Double httpVersion) throws InvalidHTTPVersionException {
        if ( !VALID_HTTP_VERSIONS.contains(httpVersion) ) {
            throw new InvalidHTTPVersionException("Invalid protocol version");
        }
    }

    public String requestToString() {
        return getRequestMethod() + " " + getRequestURI() + " " + getRequestHTTPVersion() + "\n" +
                "Host: " + getHost();
    }

    public String getHost() {
        return host;
    }

    public String getRequestMethod() {
        return method;
    }

    public String getRequestURI() {
        return uri;
    }

    public String getRequestHTTPVersion() {
        return protocolVersion;
    }

    @Override
    public String toString() {
        return requestStringRepresentation == null ? requestStringRepresentation = requestToString() : requestStringRepresentation;
    }
}
