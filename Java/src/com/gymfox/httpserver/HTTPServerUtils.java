package com.gymfox.httpserver;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gymfox.httpserver.HTTPServerExceptions.*;

public final class HTTPServerUtils {
    private static final int ARGUMENTS_COUNT = 1;
    private static final int MIN_SYSTEM_PORT_VALUE = 0;
    private static final int MAX_SYSTEM_PORT_VALUE = 65536;
    private static final List<Double> VALID_HTTP_VERSIONS = Arrays.asList(0.9, 1.0, 1.1, 2.0);
    private static final int PROTOCOL_PARTS = 2;
    private static final int FILE_NAME_PARTS = 2;
    private static final int REQUEST_PARAMETERS_COUNT = 3;

    static final File CONFIG_FILE = new File("http.conf");

    private HTTPServerUtils() {}

    static void startServer(HTTPServer httpServer) throws InterruptedException {
        Runnable r = () -> {
            try {
                httpServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        ExecutorService t = Executors.newFixedThreadPool(1);
        t.execute(r);
        Thread.sleep(60000);
        t.shutdown();
    }

    static void closeSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static File checkArguments(File inputArgumentFile) throws IOException {
        validatePath(inputArgumentFile);
        validateIsNotEmpty(inputArgumentFile);

        return inputArgumentFile;
    }

    static String checkHTTPVersion(String requestHttpVersion) throws InvalidHTTPVersionException,
            InvalidPartsHTTPVersionException {
        String newRequestHttpVersion = requestHttpVersion.toUpperCase();
        validateRequestHttpVersion(newRequestHttpVersion);

        return newRequestHttpVersion;
    }

    static void validateArgumentsCount(String[] args) throws InvalidArgumentsCountException {
        if ( args.length != 1 ) {
            throw new InvalidArgumentsCountException(String.format("Invalid arguments counts. Expected %d, but found " +
                    "%d.", ARGUMENTS_COUNT, args.length));
        }
    }

    static void validatePath(File file) throws IOException {
        if ( !file.exists() && !file.isDirectory() ) {
            throw new IOException(String.format("File \"%s\" isn't exist", file.getName()));
        }
    }

    static void validatePort(int port) throws InvalidPortException {
        if ( port < MIN_SYSTEM_PORT_VALUE || port > MAX_SYSTEM_PORT_VALUE ) {
            throw new InvalidPortException(String.format("%d is invalid port. Value between %d and %d is expected",
                    port, MIN_SYSTEM_PORT_VALUE, MAX_SYSTEM_PORT_VALUE));
        }
    }

    static void validateIsNotEmpty(File configFile) throws FileIsEmptyException {
        if ( configFile.length() == 0 ) {
            throw new FileIsEmptyException("File doesn't have any parameters.");
        }
    }

    static void validateRequestParameters(String[] parameters) throws InvalidRequestParametersCountException {
        if ( parameters.length != REQUEST_PARAMETERS_COUNT ) {
            throw new InvalidRequestParametersCountException(String.format("Invalid request parameters count. " +
                    "Expected %d, but found %d", REQUEST_PARAMETERS_COUNT, parameters.length));
        }
    }

    static void validateRequestHttpVersion(String requestHttpVersion) throws InvalidHTTPVersionException,
            InvalidPartsHTTPVersionException {
        String[] httpVersionParts = requestHttpVersion.split("/");
        validateParts(httpVersionParts);

        double httpVersion = Double.parseDouble(httpVersionParts[1]);

        if ( !httpVersionParts[0].equals("HTTP") ) {
            throw new InvalidHTTPVersionException("Invalid PROTOCOL name");
        }

        if ( !VALID_HTTP_VERSIONS.contains(httpVersion) ) {
            throw new InvalidHTTPVersionException("Invalid PROTOCOL version");
        }
    }

    static void validateParts(String[] httpVersionParts) throws InvalidPartsHTTPVersionException {
        if ( httpVersionParts.length != PROTOCOL_PARTS) {
            throw new InvalidPartsHTTPVersionException(String.format("Invalid HTTP version parts. Expected %d, but " +
                    "found %d", PROTOCOL_PARTS, httpVersionParts.length));
        }
    }

    static void validateFileName(String fileName) throws FileNameIsNullOrEmptyException {
        if ( fileName == null || fileName.trim().isEmpty()) {
            throw new FileNameIsNullOrEmptyException("File name is empty or null.");
        }
    }
}
