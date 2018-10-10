package com.gymfox.httpserver;

import static com.gymfox.httpserver.HTTPServer.httpServerConf;

public final class HTTPRequest {
    private String method;
    private String uri;
    private String protocolVersion;
    private String request;

    public HTTPRequest(String method, String uri, String protocolVersion) {
        this.method = method;
        this.uri = uri;
        this.protocolVersion = protocolVersion;
        requestToString();
    }

    public void requestToString() {
        request = "URL:\n\t" + getURL() +
                "Request:\n\t" +
                getRequestMethod() + " " +
                getRequestURI() + " " +
                getRequestHttpVersion() + "\n" +
                "\tHost: " + getHost() + "\n";
    }

    public String getRequestMethod() {
        return method;
    }

    public String getRequestURI() {
        return uri;
    }

    public String getRequestHttpVersion() {
        return protocolVersion;
    }

    public String getHost() {
        return httpServerConf.getRootDirectory().getName();
    }

    public String getHostPaths() {
        return httpServerConf.getRootDirectory().getAbsolutePath() + getRequestURI();
    }

    public String getURL() {
        return "http://" + getHost() + getRequestURI() + "\n";
    }

    @Override
    public String toString() {
        return request;
    }
}
