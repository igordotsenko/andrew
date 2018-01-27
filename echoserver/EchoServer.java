package com.gymfox.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private final int port;
    private volatile boolean isRunning;
    private BufferedReader inputMessage;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final static int DEFAULT_PORT = 8080;
    private final static int MIN_PERMISSION_PORT = 1024;
    private final static int MAX_PERMISSION_PORT = 65536;
    private ExecutorService pool = Executors.newFixedThreadPool(2);

    public static class InvalidPortException extends Exception{}

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
            throw new InvalidPortException();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getPort() {
        return port;
    }

    public void start() throws IOException {
        run();

        while (isRunning()) {
            clientSocket = serverSocket.accept();
            System.out.println("Client has been connected\n");

            pool.execute(() -> {
                try {
                    while ( clientIsNotClose() ) {
                        inputMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String line = inputMessage.readLine();

                        checkCommand(line);

                        System.out.println ("Entry message: " + line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void run() throws IOException {
        if ( !isRunning() ) {
            isRunning = true;
            serverSocket = new ServerSocket(getPort());

            System.out.println("Echo sever has been started");
        }
    }

    private void checkCommand(String line) throws IOException {
        if ( line.equals("disconnect") ) {
            disconnect();
        }
    }

    private void disconnect() throws IOException {
        inputMessage.close();
        clientSocket.close();

        System.out.println("Client has been disconnected");
    }

    private boolean clientIsNotClose() {
        return !clientSocket.isClosed();
    }

    public void stop() throws IOException {
        if ( isRunning() ) {
            if ( clientIsNotClose() ) {
                disconnect();
            }

            isRunning = false;
            pool.shutdown();
            serverSocket.close();
            System.out.println("Server has been closed");

            return;
        }
        System.out.println("Echo server is close");
    }
}

class StartEchoServer {
    public static void main(String[] args) throws IOException, EchoServer.InvalidPortException, InterruptedException {
        EchoServer server = new EchoServer();
        runServer(server);
        server.stop();
        System.exit(0);
    }

    public static void runServer(EchoServer server) throws InterruptedException {
        Runnable r = () -> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread t = new Thread(r);
        t.start();
        t.sleep(10000);
    }
}
