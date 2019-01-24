package com.gymfox.httpserver;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.METHOD_NOT_ALLOWED;
import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.NOT_FOUND_CODE;
import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.OK_CODE;
import static com.gymfox.httpserver.HTTPRequestHandler.getFilePathByUri;

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

    @Test
    public void processingRequestURITest() {
        File directory = httpServer.getHttpServerConf().getRootDirectory();

        Assert.assertEquals("/var/www/localhost/index.html", getFilePathByUri(directory,"/").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/index.html", getFilePathByUri(directory,"/index.html").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/test/style.css", getFilePathByUri(directory,"/test/style.css").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/test", getFilePathByUri(directory,"/test/").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/fakeFile.html", getFilePathByUri(directory,"/fakeFile.html").getAbsolutePath());
    }
}
