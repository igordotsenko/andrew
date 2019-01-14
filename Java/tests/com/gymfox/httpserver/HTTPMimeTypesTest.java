package com.gymfox.httpserver;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.gymfox.httpserver.HTTPMimeTypes.validateFileName;
import static com.gymfox.httpserver.HTTPServerExceptions.FileNotFoundException;

public class HTTPMimeTypesTest {

    @Test
    public void getMimeFormatTest() throws IOException {
        HTTPServer httpServer = new HTTPServer();
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
