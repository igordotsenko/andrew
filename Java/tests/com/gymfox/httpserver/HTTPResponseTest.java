package com.gymfox.httpserver;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPResponse.ResponseHeaders.ALLOWED_METHODS;
import static com.gymfox.httpserver.HTTPResponse.ResponseHeaders.CONTENT_LENGTH;
import static com.gymfox.httpserver.HTTPResponse.ResponseHeaders.CONTENT_TYPE;

public class HTTPResponseTest {
    private static HTTPRequest httpRequest;
    private static HTTPRequestHandler httpRequestHandler;

    @BeforeClass
    public static void setUpHTTPServer() throws IOException {
        HTTPServer httpServer = new HTTPServer(new File("http.conf"));
        httpRequestHandler = new HTTPRequestHandler(httpServer.getHttpServerConf());
    }

    @Test
    public void okResponseTest() throws IOException {
        httpRequest = new HTTPRequest("get", "/", "http/2.0", "localhost");
        HTTPResponse response = httpRequestHandler.handleRequest(httpRequest);
        Assert.assertEquals("HTTP/2.0", response.getHTTPVersion().get());
        Assert.assertEquals("200 OK", response.getStatusCode());
        Assert.assertEquals("Allowed: [GET, POST]", response.getHeader(ALLOWED_METHODS).orElse(""));
        Assert.assertEquals("Content-type: text/html; charset=UTF-8", response.getHeader(CONTENT_TYPE).orElse(""));
        Assert.assertEquals("Content-length: 86", response.getHeader(CONTENT_LENGTH).orElse(""));
    }

    @Test
    public void notAllowedMethodResponseTest() throws IOException {
        httpRequest = new HTTPRequest("gat", "/", "http/2.0", "localhost");
        HTTPResponse response = httpRequestHandler.handleRequest(httpRequest);

        Assert.assertEquals("HTTP/2.0", response.getHTTPVersion().get());
        Assert.assertEquals("405 Not allowed method", response.getStatusCode());
        Assert.assertEquals("Allowed: [GET, POST]", response.getHeader(ALLOWED_METHODS).orElse(""));
        Assert.assertEquals("", response.getHeader(CONTENT_TYPE).orElse(""));
        Assert.assertEquals("", response.getHeader(CONTENT_LENGTH).orElse(""));
    }

    @Test
    public void notFoundResponseTest() throws IOException {
        httpRequest = new HTTPRequest("get", "/some_strange_uri", "http/2.0", "localhost");
        HTTPResponse response = httpRequestHandler.handleRequest(httpRequest);

        Assert.assertEquals("HTTP/2.0", response.getHTTPVersion().get());
        Assert.assertEquals("404 Not Found", response.getStatusCode());
        Assert.assertEquals("Allowed: [GET, POST]", response.getHeader(ALLOWED_METHODS).orElse(""));
        Assert.assertEquals("", response.getHeader(CONTENT_LENGTH).orElse(""));
    }
}
