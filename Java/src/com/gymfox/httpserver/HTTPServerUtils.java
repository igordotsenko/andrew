package com.gymfox.httpserver;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gymfox.httpserver.HTTPServer.getHost;
import static com.gymfox.httpserver.HTTPCreateRequest.getRequestURI;

final class HTTPServerUtils {
    private static final int MIN_SYSTEM_PORT_VALUE = 0;
    private static final int MAX_SYSTEM_PORT_VALUE = 65536;
    private static final double VALID_HTTP_VERSION[] = {0.9, 2};
    private static final String INDEX_HTML = "index.html";
    static final String CONFIG_FILE = "http.conf";
    static Map<String, String> config;

    private HTTPServerUtils() {}

    public static class InvalidPortException extends Exception {
        public InvalidPortException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidPathToCurrentFileException extends Exception {
        public InvalidPathToCurrentFileException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class NotAllowedMethodException extends Exception {
        public NotAllowedMethodException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidHttpVersionException extends Exception {
        public InvalidHttpVersionException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class HttpServerIsRunningException extends Exception {
        public HttpServerIsRunningException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static void startServer(HTTPServer httpServer) throws InterruptedException {
        Runnable r = () -> {
            try {
                httpServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        ExecutorService t = Executors.newFixedThreadPool(1);
        t.execute(r);
        Thread.sleep(30000);
    }

    public static void closeSocket(Socket clientSocket) {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String getURL() {
        return "http://" + getHost() + getRequestURI() + "\n";
    }


    static File validatePath(File file) {
        if ( !file.exists() ) {
            try {
                throw new InvalidPathToCurrentFileException(String.format("File \"%s\" isn't exist", file.getName()));
            } catch (InvalidPathToCurrentFileException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    static int validatePort(int port) {
        if ( port < MIN_SYSTEM_PORT_VALUE || port > MAX_SYSTEM_PORT_VALUE ) {
            try {
                throw new InvalidPortException(String.format("%d is invalid port. Value beetwen %d and %d is expected",
                        port, MIN_SYSTEM_PORT_VALUE, MAX_SYSTEM_PORT_VALUE));
            } catch (InvalidPortException e) {
                e.printStackTrace();
            }
        }

        return port;
    }

    static String checkRequestMethod(String requestMethod) {
        validateRequestMethod(requestMethod);

        return requestMethod;
    }

    static void validateRequestMethod(String requestMethod) {
        if ( !requestMethod.equals("GET") ) {
            try {
                throw new NotAllowedMethodException("405 Method Not Allowed");
            } catch (NotAllowedMethodException e) {
                e.printStackTrace();
            }
        }
    }

    static String checkRequestURI(String requestURI) {
        String checkedURI = checkSplitURI(requestURI);

        validateRequestURI(checkedURI);

        return checkedURI;
    }

    private static String checkSplitURI(String splitURI) {
        String[] splitRequestURI = splitURI.split("/");
        String last = splitRequestURI[splitRequestURI.length-1];

        if ( !last.equals(INDEX_HTML) ) {
            return splitURI + "/" + INDEX_HTML;
        }

        return splitURI;
    }

    static void validateRequestURI(String requestURI) {
        File fileRequestURI = new File(config.get("root_dir") + "/" + requestURI);

        if ( !fileRequestURI.exists() ) {
            try {
                throw new InvalidPathToCurrentFileException("404 not found");
            } catch (InvalidPathToCurrentFileException e) {
                e.printStackTrace();
            }
        }
    }

    static String checkHttpVersion(String requestHttpVersion) {
        validateRequestHttpVersion(requestHttpVersion);

        return requestHttpVersion;
    }

    static void validateRequestHttpVersion(String requestHttpVersion) {
        String[] httpVersionParts = requestHttpVersion.split("/");
        double httpVersion = Double.parseDouble(httpVersionParts[1]);

        if ( !httpVersionParts[0].equals("HTTP") ) {
            try {
                throw new InvalidHttpVersionException("Invalid protocol name");
            } catch (InvalidHttpVersionException e) {
                e.printStackTrace();
            }
        }

        if ( httpVersion < VALID_HTTP_VERSION[0] || httpVersion > VALID_HTTP_VERSION[1] ) {
            try {
                throw new InvalidHttpVersionException("Invalid protocol version");
            } catch (InvalidHttpVersionException e) {
                e.printStackTrace();
            }
        }
    }
}
