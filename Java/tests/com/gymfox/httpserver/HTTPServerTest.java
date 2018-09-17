package com.gymfox.httpserver;

import com.gymfox.communication.Exceptions;
import com.gymfox.httpserver.HTTPServerUtils.*;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPCreateRequest.*;
import static com.gymfox.httpserver.HTTPServerUtils.*;

public class HTTPServerTest {
    private String pathToConf = "tests/com/gymfox/httpserver/configuration/";

    @Test ( expected = FileIsEmptyException.class )
    public void fileIsEmptyException() throws FileIsEmptyException {
        validateIsNotEmpty(new File(pathToConf + "EmptyFileTest.conf"));
    }

    @Test ( expected = Exceptions.InvalidValueInOctetsException.class )
    public void validateAddressTest() throws IOException {
        new HTTPServer(new File(pathToConf + "validateAddressTest.conf"));
    }

    @Test ( expected = HTTPServerUtils.InvalidPortException.class )
    public void validatePortTest() throws IOException {
        new HTTPServer(new File(pathToConf + "validatePortTest.conf"));
    }

    @Test ( expected = IOException.class )
    public void validatePathTest() throws IOException {
        new HTTPServer(new File(pathToConf + "validatePathTest.conf"));
    }

    @Test ( expected = IOException.class )
    public void validateConfigFile() throws IOException {
        new HTTPServer(new File(pathToConf + "ExistConfigFile.conf"));
        new HTTPServer(new File("httpDoesNotExist.conf"));
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
    public void getHTTPServerConfigTest() throws IOException {
        HTTPServer httpServer = new HTTPServer(new File(pathToConf + "allConfigIsOk.conf"));
        Assert.assertEquals(httpServer.getHttpServerConf().getAddress(), "127.0.0.1");
        Assert.assertEquals(httpServer.getHttpServerConf().getPort(), 80);
        Assert.assertEquals(httpServer.getHttpServerConf().getRootDirectory().toString(), "/var/www/localhost");
        Assert.assertEquals(httpServer.getHttpServerConf().toString(),
                "Configuration file:\n" +
                "address 127.0.0.1\n" +
                "port 80\n" +
                "root_dir /var/www/localhost\n\n");
    }

    @Test
    public void setRequestMethodsTest() throws IOException {
        new HTTPServer(new File(pathToConf + "allConfigIsOk.conf"));
        setRequestMethod("get");
        setRequestURI("/index.html");
        setRequestHTTPVersion("http/1.1");
        requestToString();

        Assert.assertEquals(getRequestMethod(), "GET");
        Assert.assertEquals(getRequestURI(), "/index.html");
        Assert.assertEquals(getHttpVersion(), "HTTP/1.1");
        Assert.assertEquals(getRequest(), "Request: \n\tGET /index.html HTTP/1.1\n\tHost: localhost\n");
    }

    @Test
    public void checkRequestMethodsTest() throws IOException {
        new HTTPServer(new File(pathToConf + "allConfigIsOk.conf"));

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
