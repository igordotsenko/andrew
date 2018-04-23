package com.gymfox.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gymfox.echoserver.ServerUtils.*;

public class EchoServer implements Executable {
    private final ExecutorService pool = Executors.newFixedThreadPool(2);
    private final int port;
    private volatile boolean isRunning;
    private volatile ServerSocket serverSocket;

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

    public boolean isRunning() {
        return isRunning;
    }

    public int getPort() {
        return port;
    }

    public void start() throws ServerIsAlreadyRunningException, IOException {
        runServer();

        while (isRunning()) {
            Socket clientSocket = serverSocket.accept();
            pool.execute(() -> {
                    try (PrintWriter sout = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                         BufferedReader sin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                        System.out.println("Client has been connected\n");
                        clientSocket.setSoTimeout(5000);

                        String line;

                        while ( checkLine(line = sin.readLine()) ) {
                            System.out.println("Entry message: " + line);
                            sout.println("Message from server: " + line);
                            sout.flush();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        closeSocket(clientSocket);
                    }
            });
        }
    }

    private synchronized void runServer() throws ServerIsAlreadyRunningException, IOException {
        if ( isRunning() ) {
            throw new ServerIsAlreadyRunningException("Server is already running");
        }

        isRunning = true;
        serverSocket = new ServerSocket(getPort());

        System.out.println("Echo sever has been started");
    }

    private boolean checkLine(String line) {
        return isRunning() && line != null && !line.equals("disconnect");
    }

    public synchronized void stop() throws IOException {
        if ( isRunning() ) {
            isRunning = false;
            serverSocket.close();
            pool.shutdown();

            System.out.println("Server has been closed");
        }
    }
}

class StartEchoServer {
    public static void main(String[] args) throws InvalidPortException, IOException, InterruptedException {
        EchoServer server = new EchoServer();
        startConnect(server);

        server.stop();
        System.exit(0);
    }
}
