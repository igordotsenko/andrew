package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static com.gymfox.httpserver.HTTPServerExceptions.InvalidRequestParametersCountException;
import static com.gymfox.httpserver.HTTPServerUtils.validateRequestParameters;

public final class HTTPTransformer {
    private static final int REQUEST_METHOD = 0;
    private static final int REQUEST_URI = 1;
    private static final int REQUEST_PROTOCOL = 2;

    public HTTPTransformer() {}

    public HTTPRequest readHTTPRequest(BufferedReader bufferedReader, HTTPServerConf serverConf) throws IOException,
            InvalidRequestParametersCountException {
        String[] inputRequest = checkRequestParameters(bufferedReader.readLine().split(" "));

        return new HTTPRequest(inputRequest[REQUEST_METHOD], inputRequest[REQUEST_URI],
                inputRequest[REQUEST_PROTOCOL], serverConf);
    }

    public String[] checkRequestParameters(String[] parameters) throws InvalidRequestParametersCountException {
        validateRequestParameters(parameters);

        return parameters;
    }

    public void writeHTTPResponse(HTTPResponse httpResponse, PrintWriter printWriter) {
        printWriter.println(httpResponse.sendResponse());
        printWriter.flush();
    }
}
