package com.gymfox.httpserver;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.METHOD_NOT_ALLOWED;
import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.NOT_FOUND_CODE;
import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.OK_CODE;

public class HTTPRequestHandlerTest {
    private static HTTPServer httpServer;
    private static HTTPRequest httpRequest;
    private static HTTPRequestHandler httpRequestHandler;

    @BeforeClass
    public static void setUpHTTPServer() throws IOException {
        httpServer = new HTTPServer(new File("http.conf"));
        httpRequestHandler = new HTTPRequestHandler(httpServer.getHttpServerConf());
    }

    @Test
    public void checkOkRequestParametersTest() throws IOException {
        httpRequest = new HTTPRequest("get", "/", "http/2.0", "localhost");

        Assert.assertEquals(OK_CODE.getCodeStatus() + " " + OK_CODE.getCodeName(),
                httpRequestHandler.handleRequest(httpRequest).getStatusCode());
    }

    @Test
    public void checkBadRequestParametersTest() throws IOException {
        httpRequest = new HTTPRequest("gat", "/", "http/2.0", "localhost");

        Assert.assertEquals(METHOD_NOT_ALLOWED.getCodeStatus() + " " + METHOD_NOT_ALLOWED.getCodeName(),
                httpRequestHandler.handleRequest(httpRequest).getStatusCode());
    }

    @Test
    public void checkNotFoundRequestParametersTest() throws IOException {
        httpRequest = new HTTPRequest("get", "/findex", "http/2.0", "localhost");

        Assert.assertEquals(NOT_FOUND_CODE.getCodeStatus() + " " + NOT_FOUND_CODE.getCodeName(),
                httpRequestHandler.handleRequest(httpRequest).getStatusCode());
    }
}
