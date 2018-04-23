package com.gymfox.echoserver;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ServerUtils {
    public final static int MAX_PERMISSION_PORT = 65536;
    public final static int MIN_PERMISSION_PORT = 1024;
    public final static int DEFAULT_PORT = 8080;

    private ServerUtils() {}

    public static class InvalidPortException extends Exception {
        public InvalidPortException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static void validate(int port) throws InvalidPortException {
        if ( port < MIN_PERMISSION_PORT || port > MAX_PERMISSION_PORT ) {
            throw new InvalidPortException(String.format("%d is invalid port. Value beetwen %d and %d is expected.",
                    port, MIN_PERMISSION_PORT, MAX_PERMISSION_PORT));
        }
    }

    public static void startConnect(Executable socket) throws InterruptedException {
        Runnable r = () -> {
            try {
                socket.start();
            } catch (EchoServer.ServerIsAlreadyRunningException | IOException e) {
                e.printStackTrace();
            }
        };

        ExecutorService t = Executors.newFixedThreadPool(1);
        t.execute(r);
        Thread.sleep(10000);
    }

    public static void closeSocket(Socket clientSocket) {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
