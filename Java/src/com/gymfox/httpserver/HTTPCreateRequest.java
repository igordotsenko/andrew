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
            NotAllowedMethodException, InvalidPathToCurrentFileException, InvalidHttpVersionException {
        sout.println("Enter request: ");
        sout.flush();

        String requestMethod = checkRequestMethod(sin.readLine().toUpperCase());
        requestParameters.put("method", requestMethod);

        String requestURI = checkRequestURI(sin.readLine());
        requestParameters.put("uri", requestURI);

        String requestHttpVersion = checkHttpVersion(sin.readLine().toUpperCase());
        requestParameters.put("version", requestHttpVersion);

//        requestParameters.put("host", getHost());

        requestToString();
    }

    private static String checkRequestURI(String requestURI) throws InvalidPathToCurrentFileException {
        String checkedURI = checkSplitURI(requestURI);

        validateRequestURI(checkedURI);

        return checkedURI;
    }

    private static String checkSplitURI(String splitURI) {
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

    static String getRequestMethod() {
        return requestParameters.get("method");
    }

    static String getRequestURI() {
        return requestParameters.get("uri");
    }

    static String getHttpVersion() {
        return requestParameters.get("version");
    }

//    static String getRequestHost() {
//        return requestParameters.get("host");
//    }

    static String getRequest() {
        return request;
    }
}
