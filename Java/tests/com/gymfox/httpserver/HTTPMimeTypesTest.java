package com.gymfox.httpserver;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPServer.mimeTypeFile;
import static com.gymfox.httpserver.HTTPServerExceptions.FileNameIsNullOrEmptyException;
import static com.gymfox.httpserver.HTTPServerUtils.validateFileName;

public class HTTPMimeTypesTest {
    private static final String httpServerConf = "http.conf";
    @BeforeClass
    public static void setUpHTTPServer() throws IOException {
        new HTTPServer(new File(httpServerConf));
    }

    @Test
    public void getMimeFormatTest() throws FileNameIsNullOrEmptyException {
        Assert.assertEquals("text/html; charset=UTF-8", mimeTypeFile.getMimeFormat("index.html"));
        Assert.assertEquals("text/css", mimeTypeFile.getMimeFormat("style.css"));
        Assert.assertEquals("application/x-javascript", mimeTypeFile.getMimeFormat("scenario.js"));
        Assert.assertEquals("image/jpeg", mimeTypeFile.getMimeFormat("image.jpeg"));
        Assert.assertEquals("image/jpeg", mimeTypeFile.getMimeFormat("background.jpg"));
        Assert.assertEquals("image/png", mimeTypeFile.getMimeFormat("icon.png"));
        Assert.assertEquals("application/octet-stream", mimeTypeFile.getMimeFormat("reset_button.exe"));
        Assert.assertEquals("application/octet-stream", mimeTypeFile.getMimeFormat("/someText"));
        Assert.assertEquals("application/octet-stream", mimeTypeFile.getMimeFormat("someText"));
    }

    @Test ( expected = FileNameIsNullOrEmptyException.class )
    public void validateNullFileNameTest() throws FileNameIsNullOrEmptyException {
        validateFileName(null);
    }

    @Test ( expected = FileNameIsNullOrEmptyException.class )
    public void validateEmptyFileNameTest() throws FileNameIsNullOrEmptyException {
        validateFileName("");
    }

    @Test ( expected = FileNameIsNullOrEmptyException.class )
    public void validateFileNameWithSpacesTest() throws FileNameIsNullOrEmptyException {
        validateFileName("                  ");
    }
}
