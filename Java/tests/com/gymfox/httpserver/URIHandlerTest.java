package com.gymfox.httpserver;

import org.junit.Test;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;

public class URIHandlerTest {
    private static final String httpServerConf = "http.conf";

    @Test
    public void processingRequestURITest() throws IOException {
        HTTPServer server = new HTTPServer(new File(httpServerConf));

        Assert.assertEquals("/var/www/localhost/index.html",
                URIHandler.processingRequestURI(server.getHttpServerConf().getRootDirectory(),"/").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/index.html",
                URIHandler.processingRequestURI(server.getHttpServerConf().getRootDirectory(),"/index.html").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/test/index.html",
                URIHandler.processingRequestURI(server.getHttpServerConf().getRootDirectory(),"/test/").toString());
        Assert.assertEquals("/var/www/localhost/test/style.css",
                URIHandler.processingRequestURI(server.getHttpServerConf().getRootDirectory(),"/test/style.css").toString());
        Assert.assertEquals("/var/www/localhost/fakeFile.html",
                URIHandler.processingRequestURI(server.getHttpServerConf().getRootDirectory(),"/fakeFile.html").getAbsolutePath());
    }
}
