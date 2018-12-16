package com.gymfox.httpserver;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class HTTPServerUtils {
    static final String INPUT_PARTS_DELIMITER = " ";
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

    static void validatePath(File file) throws IOException {
        if ( !file.exists() && !file.isDirectory() ) {
            throw new IOException(String.format("File \"%s\" isn't exist", file.getName()));
        }
    }
}
