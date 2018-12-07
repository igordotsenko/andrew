package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.BAD_REQUEST_CODE;
import static com.gymfox.httpserver.HTTPServerExceptions.MalformedRequestException;

public final class HTTPTransformer {
    private static final int REQUEST_METHOD = 0;
    private static final int REQUEST_URI = 1;
    private static final int REQUEST_PROTOCOL = 2;
    private static final String SPACE = " ";
    private static final int REQUEST_PARAMETERS_COUNT = 3;
    private final HTTPTransformerConfig httpServerConf;

    public HTTPTransformer(HTTPServerConf httpServerConf) {
        this.httpServerConf = httpServerConf;
    }

    public HTTPRequest readHTTPRequest(BufferedReader bufferedReader) throws IOException, MalformedRequestException {
            String[] inputRequest = checkRequestParameters(bufferedReader.readLine().split(SPACE));

            return new HTTPRequest(inputRequest[REQUEST_METHOD], inputRequest[REQUEST_URI],
                    inputRequest[REQUEST_PROTOCOL], httpServerConf.getConfigHost());
    }

    private String[] checkRequestParameters(String[] requestLine) throws MalformedRequestException {
        validateLength(requestLine);

        return requestLine;
    }

    static void validateLength(String[] requestLength) throws MalformedRequestException {
        if ( requestLength.length != REQUEST_PARAMETERS_COUNT ) {
            throw new MalformedRequestException(String.format(BAD_REQUEST_CODE.getCodeStatus() +
                            " " + BAD_REQUEST_CODE.getCodeName() +
                            ". Invalid request parameters count. Expected %d, but found %d",
                    REQUEST_PARAMETERS_COUNT, requestLength.length));
        }
    }

    public void writeHTTPResponse(HTTPResponse httpResponse, PrintWriter printWriter) {
        printWriter.println(httpResponse.getFullResponse());
        printWriter.flush();
    }
}
