package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.gymfox.httpserver.HTTPServer.getHost;
import static com.gymfox.httpserver.HTTPServerUtils.*;

public final class HTTPCreateRequest {
    private static final Map<String, String> requestParameters = new HashMap<>();
    private static volatile String request;

    private HTTPCreateRequest() {}

    static void processingRequest(PrintWriter sout, BufferedReader sin) throws IOException {
        sout.println("Enter request: ");
        sout.flush();

        String requestMethod = checkRequestMethod(sin.readLine().toUpperCase());
        requestParameters.put("method", requestMethod);

        String requestURI = checkRequestURI(sin.readLine());
        requestParameters.put("uri", requestURI);

        String requestHttpVersion = checkHttpVersion(sin.readLine().toUpperCase());
        requestParameters.put("version", requestHttpVersion);

        requestParameters.put("host", getHost());

        requestToString();
    }

    static void requestToString() {
        StringBuilder out = new StringBuilder();

        out.append("Request: \n\t");
        out.append(getRequestMethod()).append(" ");
        out.append(getRequestURI()).append(" ");
        out.append(getHttpVersion()).append("\n");
        out.append("\tHost: ").append(requestParameters.get("host")).append("\n");

        request = out.toString();
    }

    static String getRequestMethod() {
        return requestParameters.get("method");
    }

    static String getRequestURI() {
        return requestParameters.get("uri");
    }

    static String getHttpVersion() {
        return requestParameters.get("version");
    }

    static String getRequest() {
        return request;
    }
}
