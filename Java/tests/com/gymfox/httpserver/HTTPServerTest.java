package com.gymfox.httpserver;

import com.gymfox.communication.Exceptions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.ConfigSerializer.getHTTPConfig;
import static com.gymfox.httpserver.HTTPServerExceptions.*;
import static com.gymfox.httpserver.HTTPServerUtils.*;

public class HTTPServerTest {
    private static final String pathToConf = "tests/com/gymfox/httpserver/configuration/";
    private static final String mimeTypeFile = "mime.types";
    private static HTTPServer httpServerTest;

    @BeforeClass
    public static void setUpHTTPServer() throws IOException {
        httpServerTest = new HTTPServer(new File(pathToConf + "allConfigIsOk.conf"), new File(mimeTypeFile));
    }

    @Test ( expected = InvalidArgumentsCountException.class )
    public void tooMuchArgumentsCountExceptionTest() throws InvalidArgumentsCountException {
        validateArgumentsCount(new String[] {"", "", "", "", "", "", "", "", "", ""});
    }

    @Test ( expected = InvalidArgumentsCountException.class )
    public void notMuchArgumentsCountExceptionTest() throws InvalidArgumentsCountException {
        validateArgumentsCount(new String[] {});
    }

    @Test ( expected = FileIsEmptyException.class )
    public void fileIsEmptyExceptionTest() throws FileIsEmptyException {
        validateIsNotEmpty(new File(pathToConf + "EmptyFileTest.conf"));
    }

    @Test ( expected = Exceptions.InvalidValueInOctetsException.class )
    public void validateAddressTest() throws IOException {
        new HTTPServer(new File(pathToConf + "validateAddressTest.conf"), new File(mimeTypeFile));
    }

    @Test ( expected = HTTPServerExceptions.InvalidPortException.class )
    public void validatePortTest() throws IOException {
        new HTTPServer(new File(pathToConf + "validatePortTest.conf"), new File(mimeTypeFile));
    }

    @Test ( expected = IOException.class )
    public void validatePathTest() throws IOException {
        new HTTPServer(new File(pathToConf + "validatePathTest.conf"), new File(mimeTypeFile));
    }

    @Test ( expected = IOException.class )
    public void validateConfigFileTest() throws IOException {
        new HTTPServer(new File(pathToConf + "ExistConfigFile.conf"), new File(mimeTypeFile));
        new HTTPServer(new File("httpDoesNotExist.conf"), new File(mimeTypeFile));
    }

    @Test ( expected = NotAllowedMethodException.class )
    public void validateRequestMethodTest() throws NotAllowedMethodException {
        validateRequestMethod("GAT");
    }

    @Test ( expected = InvalidHttpVersionException.class )
    public void validateHTTPNameTest() throws InvalidHttpVersionException, InvalidPartsHTTPVersionException {
        validateRequestHttpVersion("HTSP/1.1");
    }

    @Test ( expected = InvalidHttpVersionException.class )
    public void validateHTTPLessVersionTest() throws InvalidHttpVersionException, InvalidPartsHTTPVersionException {
        validateRequestHttpVersion("HTTP/0.5");
    }

    @Test ( expected = InvalidHttpVersionException.class )
    public void validateHTTPMoreVersionTest() throws InvalidHttpVersionException, InvalidPartsHTTPVersionException {
        validateRequestHttpVersion("HTTP/2.5");
    }

    @Test ( expected = IOException.class )
    public void validateRequestURITest() throws IOException {
        validateRequestURI("/findex.html");
    }

    @Test ( expected = RuntimeException.class )
    public void getConfigToMuchWordsTest() throws IOException {
        getHTTPConfig(new File(pathToConf + "ToMuchWords.conf"));
    }

    @Test ( expected = RuntimeException.class )
    public void getConfigNotEnoughWordsTest() throws IOException {
        getHTTPConfig(new File(pathToConf + "NotEnoughWords.conf"));
    }

    @Test
    public void checkArgumentsTest() throws IOException {
        Assert.assertEquals(new File(pathToConf + "allConfigIsOk.conf"), checkArguments(new File(pathToConf + "allConfigIsOk.conf")));
    }

    @Test
    public void validateHTTPVersionTest() throws InvalidHttpVersionException, InvalidPartsHTTPVersionException {
        validateRequestHttpVersion("HTTP/1.0");
    }

    @Test ( expected = InvalidPartsHTTPVersionException.class )
    public void validatePartsTest() throws InvalidPartsHTTPVersionException {
        validateParts(new String[]{"HTTP", "1.1", "i", "want", "to", "break", "free"});
    }

    @Test ( expected = InvalidPartsHTTPVersionException.class )
    public void validateNonePartsTest() throws InvalidPartsHTTPVersionException {
        validateParts(new String[]{});
    }

    @Test
    public void getHTTPServerConfigTest() {
        Assert.assertEquals(httpServerTest.getHttpServerConf(), "Configuration file:\n\t" +
                "address 127.0.0.1\n\t" +
                "port 80\n\t" +
                "root_dir /var/www/localhost\n");
    }

    @Test
    public void creationRequestTest() {
        HTTPRequest request = new HTTPRequest("GET", "/index.html", "HTTP/1.1");

        Assert.assertEquals(request.toString(), "URL:\n" +
                "\thttp://localhost/index.html\n" +
                "Request:\n" +
                "\tGET /index.html HTTP/1.1\n" +
                "\tHost: localhost\n");
    }

    @Test
    public void checkRequestMethodsTest() throws IOException {
        Assert.assertEquals(checkRequestURI("/index.html"), "/index.html");
        Assert.assertEquals(checkRequestMethod("get"), "GET");
        Assert.assertEquals(checkHttpVersion("HTTP/1.0"), "HTTP/1.0");
    }

    @Test
    public void checkSplitURITest() {
        Assert.assertEquals(checkSplitURI("/index.html"), "/index.html");
        Assert.assertEquals(checkSplitURI("google.com"), "google.com/index.html");
        Assert.assertEquals(checkSplitURI("wiki/HTTP"), "wiki/HTTP/index.html");
    }
}
