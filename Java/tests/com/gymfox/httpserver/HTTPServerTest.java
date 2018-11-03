package com.gymfox.httpserver;

import com.gymfox.communication.Exceptions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.ConfigSerializer.getHTTPConfig;
import static com.gymfox.httpserver.HTTPServerExceptions.FileIsEmptyException;
import static com.gymfox.httpserver.HTTPServerExceptions.InvalidArgumentsCountException;
import static com.gymfox.httpserver.HTTPServerExceptions.InvalidRequestParametersCountException;
import static com.gymfox.httpserver.HTTPServerUtils.*;
import static com.gymfox.httpserver.HTTPServerUtils.validateRequestParameters;

public class HTTPServerTest {
    private static final String pathToConf = "tests/com/gymfox/httpserver/configuration/";
    private static HTTPServer httpServer;

    @BeforeClass
    public static void setUpHTTPServer() throws IOException {
        httpServer = new HTTPServer(new File(pathToConf + "allConfigIsOk.conf"));
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
        new HTTPServer(new File(pathToConf + "validateAddressTest.conf"));
    }

    @Test ( expected = HTTPServerExceptions.InvalidPortException.class )
    public void validatePortTest() throws IOException {
        new HTTPServer(new File(pathToConf + "validatePortTest.conf"));
    }

    @Test
    public void validatePathTest() throws IOException {
        validatePath(new File(pathToConf + "validatePathTest.conf"));
    }

    @Test ( expected = IOException.class )
    public void validateConfigFileTest() throws IOException {
        new HTTPServer(new File(pathToConf + "ExistConfigFile.conf"));
        new HTTPServer(new File("httpDoesNotExist.conf"));
    }

    @Test
    public void validateRequestParametersTest() throws InvalidRequestParametersCountException {
        validateRequestParameters(new String[]{"get", "/index.html", "http/1.1"});
    }

    @Test ( expected = InvalidRequestParametersCountException.class )
    public void TooMuchRequestParametersTest() throws InvalidRequestParametersCountException {
        validateRequestParameters(new String[]{"get", "/index.html", "http/1.1", "localhost", "Don't", "Stop", "Me", "Now"});
    }

    @Test ( expected = RuntimeException.class )
    public void getConfigToMuchWordsTest() throws IOException {
        getHTTPConfig(new File(pathToConf + "ToMuchWords.conf"));
    }

    @Test ( expected = RuntimeException.class )
    public void getConfigNotEnoughWordsTest() throws IOException {
        getHTTPConfig(new File(pathToConf + "NotEnoughWordsTest.conf"));
    }

    @Test
    public void checkArgumentsTest() throws IOException {
        Assert.assertEquals(new File(pathToConf + "allConfigIsOk.conf"), checkArguments(new File(pathToConf + "allConfigIsOk.conf")));
    }

    @Test
    public void getHTTPServerConfigTest() {
        Assert.assertEquals("Configuration file:\n\t" +
                "address 127.0.0.1\n\t" +
                "port 80\n\t" +
                "root_dir /var/www/localhost\n\t" +
                "pool_size 2\n\t" +
                "mime_types mime.types", httpServer.getHTTPServerConfAsString());
    }
}
