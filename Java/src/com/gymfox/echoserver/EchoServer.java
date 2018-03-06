package com.gymfox.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private final static int DEFAULT_PORT = 8080;
    private final static int MIN_PERMISSION_PORT = 1024;
    private final static int MAX_PERMISSION_PORT = 65536;
    private final int port;
    private volatile boolean isRunning;
    private volatile ServerSocket serverSocket;
    private ExecutorService pool = Executors.newFixedThreadPool(2);

    public static class InvalidPortException extends Exception {
        public InvalidPortException(String errorMessage) {
            super(errorMessage);
        }

    }

    public static class ServerIsAlreadyRunningException extends Exception {
        public ServerIsAlreadyRunningException(String errorMessage) {
            super(errorMessage);
        }
    }

    public EchoServer() throws InvalidPortException {
        this(DEFAULT_PORT);
    }

    public EchoServer(int port) throws InvalidPortException {
        validate(port);
        this.port = port;
        this.isRunning = false;
    }

    private void validate(int port) throws InvalidPortException {
        if ( port < MIN_PERMISSION_PORT || port > MAX_PERMISSION_PORT ) {
            throw new InvalidPortException(String.format("%d is invalid port. Value beetwen %d and %d is expected.",
                    port, MIN_PERMISSION_PORT, MAX_PERMISSION_PORT));
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getPort() {
        return port;
    }

    public synchronized void start() throws ServerIsAlreadyRunningException, IOException {
        run();

        while (isRunning()) {
            pool.execute(() -> {
                 try (Socket clientSocket = serverSocket.accept();
                      BufferedReader sin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                     System.out.println("Client has been connected\n");
                     clientSocket.setSoTimeout(5000);

                     String line;

                     while ( checkLine(line = sin.readLine()) ) {
                         System.out.println("Entry message: " + line);
                     }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private boolean checkLine(String line) {
        return isRunning() && line != null && !line.equals("disconnect");
    }

    private synchronized void run() throws ServerIsAlreadyRunningException, IOException {
        if ( isRunning() ) {
            throw new ServerIsAlreadyRunningException("Server is already running");
        }

        if ( !isRunning() ) {
            isRunning = true;
            serverSocket = new ServerSocket(getPort());

            System.out.println("Echo sever has been started");
        }
    }

    public synchronized void stop() throws IOException {
        if ( isRunning() ) {
            pool.shutdown();
            serverSocket.close();
            isRunning = false;

            System.out.println("Server has been closed");
        }
    }
}

class StartEchoServer {
    public static void main(String[] args) throws EchoServer.InvalidPortException, InterruptedException, IOException {
        EchoServer server = new EchoServer();
        runServer(server);
        runServer(server);

//        server.stop();

        System.exit(0);
    }

    public static void runServer(EchoServer server) throws InterruptedException, IOException {
        System.out.println("Start server");
        Runnable r = () -> {
            try {
                server.start();
            } catch (EchoServer.ServerIsAlreadyRunningException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        ExecutorService t = Executors.newFixedThreadPool(1);
        t.execute(r);
        server.stop();
        Thread.sleep(100);
    }
}
