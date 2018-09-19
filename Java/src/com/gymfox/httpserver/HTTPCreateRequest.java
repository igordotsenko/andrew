package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import static com.gymfox.httpserver.HTTPServer.httpServerConf;
import static com.gymfox.httpserver.HTTPServerConf.getHost;
import static com.gymfox.httpserver.HTTPServerUtils.*;
import static com.gymfox.httpserver.HTTPServerExceptions.*;

final class HTTPCreateRequest {
    private final Map<String, String> requestParameters = new HashMap<>();
    private static String request;

    /**
     * That constructor is needed for tests.
     */
    HTTPCreateRequest() {}

    HTTPCreateRequest(PrintWriter sout, BufferedReader sin) throws IOException {
        processingRequest(sout, sin);
    }

    private void processingRequest(PrintWriter sout, BufferedReader sin) throws IOException {
        sout.println("Enter request: ");
        sout.flush();

        setRequestMethod(sin.readLine());
        setRequestURI(sin.readLine());
        setRequestHTTPVersion(sin.readLine());

        requestToString();
    }

    static String checkRequestURI(String requestURI) throws IOException {
        String checkedURI = checkSplitURI(requestURI);

        validateRequestURI(checkedURI);

        return checkedURI;
    }

    static String checkSplitURI(String splitURI) {
        String[] splitRequestURI = splitURI.split("/");
        String last = splitRequestURI[splitRequestURI.length-1];

        if ( !last.equals(INDEX_HTML) ) {
            return splitURI + "/" + INDEX_HTML;
        }

        return splitURI;
    }

    static void validateRequestURI(String requestURI) throws ProtocolException {
        File fileRequestURI = new File(httpServerConf.getRootDirectory() + "/" + requestURI);

        if ( !fileRequestURI.exists() ) {
            throw new ProtocolException("404 not found");
        }
    }

    void requestToString() {
        request = "URL:\n\t" + getURL() +
                "Request:\n\t" +
                getRequestMethod() + " " +
                getRequestURI() + " " +
                getHttpVersion() + "\n" +
                "\tHost: " + getHost() + "\n";
    }

    void setRequestMethod(String requestMethod) throws NotAllowedMethodException {
        requestParameters.put("method", checkRequestMethod(requestMethod));
    }

    void setRequestURI(String requestURI) throws IOException {
        requestParameters.put("uri", checkRequestURI(requestURI));
    }

    void setRequestHTTPVersion(String requestHttpVersion) throws InvalidHttpVersionException, InvalidPartsHTTPVersionException {
        requestParameters.put("version", checkHttpVersion(requestHttpVersion));
    }

    String getRequestMethod() {
        return requestParameters.get("method");
    }

    String getRequestURI() {
        return requestParameters.get("uri");
    }

    String getHttpVersion() {
        return requestParameters.get("version");
    }

    String getURL() {
        return "http://" + getHost() + getRequestURI() + "\n";
    }

    static String getRequest() {
        return request;
    }
}
