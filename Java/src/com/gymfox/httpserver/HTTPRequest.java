package com.gymfox.httpserver;

import java.util.HashMap;
import java.util.Map;

import static com.gymfox.httpserver.HTTPServer.httpServerConf;

final class HTTPRequest {
    private final Map<String, String> requestParameters = new HashMap<>();
    private String request;

    HTTPRequest(String method, String URI, String protocol) {
        requestParameters.put("method", method);
        requestParameters.put("uri", URI);
        requestParameters.put("version", protocol);

        requestToString();
    }

    void requestToString() {
        request = "URL:\n\t" + getURL() +
                "Request:\n\t" +
                getRequestMethod() + " " +
                getRequestURI() + " " +
                getRequestHttpVersion() + "\n" +
                "\tHost: " + getHost() + "\n";
    }

    String getRequestMethod() {
        return requestParameters.get("method");
    }

    String getRequestURI() {
        return requestParameters.get("uri");
    }

    String getRequestHttpVersion() {
        return requestParameters.get("version");
    }

    String getHost() {
        return httpServerConf.getRootDirectory().getName();
    }

    String getHostPaths() {
        return httpServerConf.getRootDirectory().getAbsolutePath() + getRequestURI();
    }

    String getURL() {
        return "http://" + getHost() + getRequestURI() + "\n";
    }

    @Override
    public String toString() {
        return request;
    }
}
