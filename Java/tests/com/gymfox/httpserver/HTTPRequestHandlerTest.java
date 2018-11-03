package com.gymfox.httpserver;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.OK_CODE;
import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.BAD_REQUEST_CODE;
import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.NOT_FOUND_CODE;

public class HTTPRequestHandlerTest {
    private static HTTPServer httpServer;
    private static HTTPRequest httpRequest;
    private static HTTPRequestHandler httpRequestHandler;

    @BeforeClass
    public static void setUpHTTPServer() throws IOException {
        httpServer = new HTTPServer(new File("http.conf"));
        httpRequestHandler = new HTTPRequestHandler();
    }

    @Test
    public void checkOkRequestParametersTest() throws IOException {
        httpRequest = new HTTPRequest("get", "/", "http/2.0", httpServer.getHttpServerConf());
        File fullPath = URIHandler.processingRequestURI(httpRequest.getConfig().getRootDirectory(), httpRequest.getRequestURI());

        Assert.assertEquals(OK_CODE, httpRequestHandler.checkRequestParameters(httpRequest, fullPath));
    }

    @Test
    public void checkBadRequestParametersTest() throws IOException {
        httpRequest = new HTTPRequest("gat", "/", "http/2.0", httpServer.getHttpServerConf());
        File fullPath = URIHandler.processingRequestURI(httpRequest.getConfig().getRootDirectory(), httpRequest.getRequestURI());

        Assert.assertEquals(BAD_REQUEST_CODE, httpRequestHandler.checkRequestParameters(httpRequest, fullPath));
    }

    @Test
    public void checkNotFoundRequestParametersTest() throws IOException {
        httpRequest = new HTTPRequest("get", "/findex", "http/2.0", httpServer.getHttpServerConf());
        File fullPath = URIHandler.processingRequestURI(httpRequest.getConfig().getRootDirectory(), httpRequest.getRequestURI());

        Assert.assertEquals(NOT_FOUND_CODE, httpRequestHandler.checkRequestParameters(httpRequest, fullPath));
    }

    @Test
    public void isOkStatusTest() {
        Assert.assertTrue(httpRequestHandler.isOkStatus(OK_CODE));
        Assert.assertFalse(httpRequestHandler.isOkStatus(BAD_REQUEST_CODE));
        Assert.assertFalse(httpRequestHandler.isOkStatus(NOT_FOUND_CODE));
    }

    @Test
    public void isBadRequestStatusTest() {
        Assert.assertFalse(httpRequestHandler.isBadRequestStatus(OK_CODE));
        Assert.assertTrue(httpRequestHandler.isBadRequestStatus(BAD_REQUEST_CODE));
        Assert.assertFalse(httpRequestHandler.isBadRequestStatus(NOT_FOUND_CODE));
    }
}
