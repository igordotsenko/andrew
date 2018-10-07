package com.gymfox.httpserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.gymfox.httpserver.HTTPServer.mimeTypeFile;

public class HTTPRequestHandler {
    public HTTPRequestHandler() {}

    HTTPResponse handleRequest(HTTPRequest httpRequest) throws IOException {

        return new HTTPResponse(httpRequest.getRequestHttpVersion(),
                new File(httpRequest.getHostPaths()).length(),
                mimeTypeFile.getMimeFormat(httpRequest.getRequestURI()),
                new String(Files.readAllBytes(Paths.get(httpRequest.getHostPaths()))));
    }
}
