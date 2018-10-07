package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static com.gymfox.httpserver.HTTPServerUtils.*;

public class HTTPTransformer {
    private static final int REQUEST_METHOD = 0;
    private static final int REQUEST_URI = 1;
    private static final int REQUEST_PROTOCOL = 2;

    HTTPTransformer() {}

    HTTPRequest readHTTPRequest(BufferedReader bufferedReader) throws IOException {
        String[] inputRequest = (bufferedReader.readLine()).split(" ");

        return new HTTPRequest(checkRequestMethod(inputRequest[REQUEST_METHOD]),
                checkRequestURI(inputRequest[REQUEST_URI]),
                checkHttpVersion(inputRequest[REQUEST_PROTOCOL]));
    }

    void writeHTTPResponse(HTTPResponse httpResponse, PrintWriter printWriter) {
        httpResponse.createResponse();

        printWriter.println(httpResponse.getResponse());
        printWriter.flush();
    }
}
