package com.gymfox.httpserver;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPServerExceptions.FileNotFoundException;
import static com.gymfox.httpserver.HTTPMimeTypes.validateFileName;

public class HTTPMimeTypesTest {
    private static final String httpServerConf = "http.conf";
    private static HTTPServer httpServer;
    @BeforeClass
    public static void setUpHTTPServer() throws IOException {
       httpServer = new HTTPServer(new File(httpServerConf));
    }

    @Test
    public void getMimeFormatTest() throws FileNotFoundException {
        Assert.assertEquals("text/html; charset=UTF-8", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("index.html"));
        Assert.assertEquals("text/css", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("style.css"));
        Assert.assertEquals("application/x-javascript", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("scenario.js"));
        Assert.assertEquals("image/jpeg", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("image.jpeg"));
        Assert.assertEquals("image/jpeg", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("background.jpg"));
        Assert.assertEquals("image/png", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("icon.png"));
        Assert.assertEquals("application/octet-stream", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("reset_button.exe"));
        Assert.assertEquals("application/octet-stream", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("/someText"));
        Assert.assertEquals("application/octet-stream", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("/someText."));
        Assert.assertEquals("application/octet-stream", httpServer.getHttpServerConf().getMimeTypes().getMimeFormat("someText"));
    }

    @Test ( expected = FileNotFoundException.class )
    public void validateNullFileNameTest() throws FileNotFoundException {
        validateFileName(null);
    }

    @Test ( expected = FileNotFoundException.class )
    public void validateEmptyFileNameTest() throws FileNotFoundException {
        validateFileName("");
    }

    @Test ( expected = FileNotFoundException.class )
    public void validateFileNameWithSpacesTest() throws FileNotFoundException {
        validateFileName("                  ");
    }
}
