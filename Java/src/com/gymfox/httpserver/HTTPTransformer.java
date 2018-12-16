package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.BAD_REQUEST_CODE;
import static com.gymfox.httpserver.HTTPServerExceptions.MalformedRequestException;
import static com.gymfox.httpserver.HTTPServerUtils.INPUT_PARTS_DELIMITER;

public final class HTTPTransformer {
    private static final int REQUEST_METHOD = 0;
    private static final int REQUEST_URI = 1;
    private static final int REQUEST_PROTOCOL = 2;
    private static final int REQUEST_PARAMETERS_COUNT = 3;
    private final HTTPTransformerConfig httpTransformerConfig;

    public HTTPTransformer(HTTPTransformerConfig httpTransformerConfig) {
        this.httpTransformerConfig = httpTransformerConfig;
    }

    public HTTPRequest readHTTPRequest(BufferedReader bufferedReader) throws IOException, MalformedRequestException {
            String[] inputRequestParts = checkRequestParameters(bufferedReader.readLine().split(INPUT_PARTS_DELIMITER));

            return new HTTPRequest(inputRequestParts[REQUEST_METHOD], inputRequestParts[REQUEST_URI],
                    inputRequestParts[REQUEST_PROTOCOL], httpTransformerConfig.getConfigHost());
    }

    private String[] checkRequestParameters(String[] requestLine) throws MalformedRequestException {
        validateLength(requestLine);

        return requestLine;
    }

    static void validateLength(String[] requestLength) throws MalformedRequestException {
        if ( requestLength.length != REQUEST_PARAMETERS_COUNT ) {
            throw new MalformedRequestException(String.format(BAD_REQUEST_CODE.getCodeStatus() +
                            INPUT_PARTS_DELIMITER + BAD_REQUEST_CODE.getCodeName() +
                            ". Invalid request parameters count. Expected %d, but found %d",
                    REQUEST_PARAMETERS_COUNT, requestLength.length));
        }
    }

    public void writeHTTPResponse(HTTPResponse httpResponse, PrintWriter printWriter) {
        printWriter.println(httpResponse.getFullResponse());
        printWriter.flush();
    }
}
