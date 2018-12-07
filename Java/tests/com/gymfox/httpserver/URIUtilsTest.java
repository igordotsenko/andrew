package com.gymfox.httpserver;

import org.junit.Test;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;

public class URIUtilsTest {
    private static final String httpServerConf = "http.conf";

    @Test
    public void processingRequestURITest() throws IOException {
        HTTPServer server = new HTTPServer(new File(httpServerConf));
        File directory = server.getHttpServerConf().getRootDirectory();

        Assert.assertEquals("/var/www/localhost/index.html", URIUtils.processingRequestURI(directory,"/").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/index.html", URIUtils.processingRequestURI(directory,"/index.html").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/test/style.css", URIUtils.processingRequestURI(directory,"/test/style.css").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/test", URIUtils.processingRequestURI(directory,"/test/").getAbsolutePath());
        Assert.assertEquals("/var/www/localhost/fakeFile.html", URIUtils.processingRequestURI(directory,"/fakeFile.html").getAbsolutePath());
    }
}
