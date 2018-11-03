package com.gymfox.httpserver;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class HTTPResponseTest {
    private static HTTPServer httpServer;
    private static HTTPRequest httpRequest;
    private static HTTPRequestHandler httpRequestHandler;

    @BeforeClass
    public static void setUpHTTPServer() throws IOException {
        httpServer = new HTTPServer(new File("http.conf"));
        httpRequestHandler = new HTTPRequestHandler();
    }

    @Test
    public void okResponseTest() throws IOException {
        httpRequest = new HTTPRequest("get", "/", "http/2.0", httpServer.getHttpServerConf());
        HTTPResponse response = httpRequestHandler.handleRequest(httpRequest);

        Assert.assertEquals("HTTP/2.0 200 OK\n" +
                "Host: localhost\n" +
                "Allowed: [GET, POST]\n" +
                "Connection: Closed\n" +
                "Content-length: 87\n" +
                "Content-type: text/html; charset=UTF-8\n" +
                "Date: " + httpRequestHandler.getServerCurrentTime() + "\n" +
                response.getResponseBody(),
                response.buildResponse());
    }

    @Test
    public void badRequestResponseTest() throws IOException {
        httpRequest = new HTTPRequest("gat", "/", "http/2.0", httpServer.getHttpServerConf());
        HTTPResponse response = httpRequestHandler.handleRequest(httpRequest);

        Assert.assertEquals("HTTP/2.0 405 Bad Request\n" +
                "Host: localhost\n" +
                "Allowed: [GET, POST]\n" +
                "Connection: Closed\n" +
                "Date: " + httpRequestHandler.getServerCurrentTime() + "\n",
                response.buildResponse());
    }

    @Test
    public void notFoundResponseTest() throws IOException {
        httpRequest = new HTTPRequest("get", "/some_strange_uri", "http/2.0", httpServer.getHttpServerConf());
        HTTPResponse response = httpRequestHandler.handleRequest(httpRequest);

        Assert.assertEquals("HTTP/2.0 404 Not Found\n" +
                "Host: localhost\n" +
                "Allowed: [GET, POST]\n" +
                "Connection: Closed\n" +
                "Content-type: application/octet-stream\n" +
                "Date: " + httpRequestHandler.getServerCurrentTime() + "\n",
                response.buildResponse());
    }
}
