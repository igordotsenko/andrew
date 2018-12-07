package com.gymfox.httpserver;

import com.gymfox.communication.Exceptions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.ConfigSerializer.getHTTPConfig;
import static com.gymfox.httpserver.HTTPServer.checkArguments;
import static com.gymfox.httpserver.HTTPServer.validateArgumentsCount;
import static com.gymfox.httpserver.HTTPServer.validateIsNotEmpty;
import static com.gymfox.httpserver.HTTPServerExceptions.FileIsEmptyException;
import static com.gymfox.httpserver.HTTPServerExceptions.InvalidArgumentsCountException;
import static com.gymfox.httpserver.HTTPServerUtils.validatePath;

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
    public void withoutAddressInConfigTest() throws IOException {
        HTTPServer server = new HTTPServer(new File(pathToConf + "noAddressTest.conf"));
        Assert.assertEquals("127.0.0.1", server.getHttpServerConf().getAddress());
    }

    @Test
    public void withoutPortInConfigTest() throws IOException {
        HTTPServer server = new HTTPServer(new File(pathToConf + "noPortTest.conf"));
        Assert.assertEquals(8080, server.getHttpServerConf().getPort());
    }

    @Test
    public void withoutRootDirInConfigTest() throws IOException {
        HTTPServer server = new HTTPServer(new File(pathToConf + "noRootDirTest.conf"));
        Assert.assertEquals("/var/www/localhost", server.getHttpServerConf().getRootDirectory().getAbsolutePath());
    }

    @Test
    public void withoutPoolSizeInConfigTest() throws IOException {
        HTTPServer server = new HTTPServer(new File(pathToConf + "noPoolSizeTest.conf"));
        Assert.assertEquals(2, server.getHttpServerConf().getPoolSize());
    }

    @Test
    public void withoutMimeTypesInConfigTest() throws IOException {
        HTTPServer server = new HTTPServer(new File(pathToConf + "noMimeTypesTest.conf"));
        Assert.assertEquals("mime.types", server.getHttpServerConf().getConfigMimeTypes().getName());
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
