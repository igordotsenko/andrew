package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.gymfox.httpserver.HTTPServer.getHost;
import static com.gymfox.httpserver.HTTPServer.httpServerConf;
import static com.gymfox.httpserver.HTTPServerUtils.*;

final class HTTPCreateRequest {
    private static final Map<String, String> requestParameters = new HashMap<>();
    private static volatile String request;

    private HTTPCreateRequest() {}

    static void processingRequest(PrintWriter sout, BufferedReader sin) throws IOException,
            NotAllowedMethodException, InvalidHttpVersionException, InvalidPartsHTTPVersionException {
        sout.println("Enter request: ");
        sout.flush();

        setRequestMethod(sin.readLine());
        setRequestURI(sin.readLine());
        setRequestHTTPVersion(sin.readLine());

        requestToString();
    }

    static String checkRequestURI(String requestURI) throws InvalidPathToCurrentFileException {
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

    static void validateRequestURI(String requestURI) throws InvalidPathToCurrentFileException {
        File fileRequestURI = new File(httpServerConf.getRootDirectory() + "/" + requestURI);

        if ( !fileRequestURI.exists() ) {
            throw new InvalidPathToCurrentFileException("404 not found");
        }
    }

    static void requestToString() {

        request = "Request: \n\t" +
                getRequestMethod() + " " +
                getRequestURI() + " " +
                getHttpVersion() + "\n" +
                "\tHost: " + getHost() + "\n";
    }

    static void setRequestMethod(String requestMethod) throws NotAllowedMethodException {
        requestParameters.put("method", checkRequestMethod(requestMethod));
    }

    static void setRequestURI(String requestURI) {
        requestParameters.put("uri", checkSplitURI(requestURI));
    }

    static void setRequestHTTPVersion(String requestHttpVersion) throws InvalidHttpVersionException, InvalidPartsHTTPVersionException {
        requestParameters.put("version", checkHttpVersion(requestHttpVersion));
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
