package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;
import com.gymfox.httpserver.HTTPServerUtils.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPCreateRequest.validateRequestURI;
import static com.gymfox.httpserver.HTTPServerUtils.*;

public class HTTPServerTest {
    private String pathToConf = "tests/com/gymfox/httpserver/configuration/";

    @Test ( expected = FileIsEmptyException.class )
    public void fileIsEmptyException() throws FileIsEmptyException {
        validateIsNotEmpty(new File(pathToConf + "EmptyFileTest.conf"));
    }

    @Test ( expected = IPv4Address.InvalidValueInOctetsException.class )
    public void validateAddressTest() throws IOException, HTTPServerUtils.InvalidPathToCurrentFileException, InvalidPortException {
        new HTTPServer(new File(pathToConf + "validateAddressTest.conf"));
    }

    @Test ( expected = HTTPServerUtils.InvalidPortException.class )
    public void validatePortTest() throws IOException, HTTPServerUtils.InvalidPathToCurrentFileException, InvalidPortException {
        new HTTPServer(new File(pathToConf + "validatePortTest.conf"));
    }

    @Test ( expected = HTTPServerUtils.InvalidPathToCurrentFileException.class )
    public void validatePathTest() throws IOException, HTTPServerUtils.InvalidPathToCurrentFileException, InvalidPortException {
        new HTTPServer(new File(pathToConf + "validatePathTest.conf"));
    }

    @Test ( expected = HTTPServerUtils.InvalidPathToCurrentFileException.class )
    public void validateConfigFile() throws IOException, HTTPServerUtils.InvalidPathToCurrentFileException, InvalidPortException {
        new HTTPServer(new File(pathToConf + "ExistConfigFile.conf"));
        new HTTPServer(new File("httpIsNotExist.conf"));
    }

    @Test ( expected = NotAllowedMethodException.class )
    public void validateRequestMethodTest() throws NotAllowedMethodException {
        validateRequestMethod("GAT");
    }

    @Test ( expected = InvalidHttpVersionException.class )
    public void validateHTTPNameTest() throws InvalidHttpVersionException {
        validateRequestHttpVersion("HTSP/1.1");
    }

    @Test ( expected = InvalidHttpVersionException.class )
    public void validateHTTPLessVersionTest() throws InvalidHttpVersionException {
        validateRequestHttpVersion("HTSP/0.5");
    }

    @Test ( expected = InvalidHttpVersionException.class )
    public void validateHTTPMoreVersionTest() throws InvalidHttpVersionException {
        validateRequestHttpVersion("HTSP/2.5");
    }

    @Test ( expected = InvalidPathToCurrentFileException.class )
    public void validateRequestURITest() throws HTTPServerUtils.InvalidPathToCurrentFileException {
        validateRequestURI("/findex.html");
    }

    @Test
    public void validateHTTPVersionTest() throws InvalidHttpVersionException {
        validateRequestHttpVersion("HTTP/1.0");
    }

    @Test
    public void allConfigIsOkTest() throws IOException, HTTPServerUtils.InvalidPathToCurrentFileException, InvalidPortException {
        new HTTPServer(new File(pathToConf + "allConfigIsOk.conf"));
    }
}
