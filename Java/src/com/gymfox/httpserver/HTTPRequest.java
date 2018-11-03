package com.gymfox.httpserver;

import java.io.IOException;

import static com.gymfox.httpserver.HTTPServerUtils.checkHTTPVersion;

public final class HTTPRequest {
    private String method;
    private String uri;
    private String protocolVersion;
    private String host;
    private HTTPServerConf config;
    private String requestStringRepresentation;

    public HTTPRequest(String method, String inputURI, String protocolVersion, HTTPServerConf conf) throws
            IOException {
        this.method = method.toUpperCase();
        this.uri = inputURI;
        this.protocolVersion = checkHTTPVersion(protocolVersion);
        this.config = conf;
        this.host = config.getConfigHost();
    }

    public String requestToString() {
        return getRequestMethod() + " " + getRequestURI() + " " + getRequestHTTPVersion() + "\n" +
                 "Host: " + getHost();
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

    public String getHost() {
        return host;
    }

    public HTTPServerConf getConfig() {
        return config;
    }

    @Override
    public String toString() {
        return requestStringRepresentation == null ? requestStringRepresentation = requestToString() : requestStringRepresentation;
    }
}
