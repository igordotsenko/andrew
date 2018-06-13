package com.gymfox.httpserver;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gymfox.httpserver.HTTPCreateRequest.*;
import static com.gymfox.httpserver.HTTPCreateResponse.createResponse;
import static com.gymfox.httpserver.HTTPCreateResponse.getResponse;
import static com.gymfox.httpserver.HTTPServerUtils.*;

public class HTTPServer {
    private final ExecutorService pool = Executors.newFixedThreadPool(1);
    private volatile ServerSocket serverSocket;
    private volatile boolean isRunning;
    static HTTPServerConf httpServerConf;

    public HTTPServer() throws IOException, InvalidPathToCurrentFileException, InvalidPortException {
        this(CONFIG_FILE);
    }

    public HTTPServer(File pathToConfigFile) throws IOException, InvalidPathToCurrentFileException,
            InvalidPortException {
        validatePath(pathToConfigFile);
        httpServerConf = ConfigSerializer.getConfig(pathToConfigFile);
    }

    public void start() throws IOException {
        runHttpServer();

        while (isRunning()) {
            Socket socket = serverSocket.accept();
            pool.execute(() -> {
                try (PrintWriter sout = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    System.out.println("Client has been connected");

                    while (isRunning()) {
                        processingRequest(sout, sin);

                        HttpURLConnection connection = (HttpURLConnection) new URL(getURL()).openConnection();

                        connection.setRequestMethod(getRequestMethod());

                        if ( connection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                            createResponse(connection);
                        }

                        sout.println(getResponse());
                        sout.flush();

                        connection.disconnect();
                    }
                } catch (IOException | InvalidHttpVersionException | InvalidPathToCurrentFileException | NotAllowedMethodException e) {
                    e.printStackTrace();
                } finally {
                    closeSocket(socket);
                }
            });
        }
    }

    private void runHttpServer() throws IOException {
        if ( isRunning() ) {
            try {
                throw new HttpServerIsRunningException("Server is already running");
            } catch (HttpServerIsRunningException e) {
                e.printStackTrace();
            }
        }

        isRunning = true;
        serverSocket = new ServerSocket(httpServerConf.getPort());

        System.out.println("HTTP sever has been started");
    }

    public void stopHttpServer() throws IOException {
        if (isRunning()) {
            isRunning = false;
            serverSocket.close();
            pool.shutdown();

            System.out.println("HTTP Server has been closed");
        }
    }

    public static String getHost() {
        return httpServerConf.getRootDirectory().getName();
    }

    public String getURL() {
        return "http://" + getHost() + getRequestURI() + "\n";
    }

    public boolean isRunning() {
        return isRunning;
    }

    public HTTPServerConf getHttpServerConf() {
        return httpServerConf;
    }

    @Override
    public String toString() {

        return String.valueOf(getHttpServerConf()) +
                "URL:\n\t" +
                getURL() +
                getRequest() +
                getResponse();
    }

    public static void main(String[] args) throws IOException, InterruptedException,
            HTTPServerUtils.InvalidPathToCurrentFileException, HTTPServerUtils.InvalidPortException, FileIsEmptyException {
        File configFile = validateIsNotEmpty(new File(args[0]));

        HTTPServer httpServer = new HTTPServer(configFile);

        startServer(httpServer);
        httpServer.stopHttpServer();

        System.exit(0);
    }
}
