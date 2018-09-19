package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gymfox.httpserver.HTTPCreateRequest.getRequest;
import static com.gymfox.httpserver.HTTPCreateResponse.createResponse;
import static com.gymfox.httpserver.HTTPCreateResponse.getResponse;
import static com.gymfox.httpserver.HTTPServerUtils.*;
import static com.gymfox.httpserver.HTTPServerExceptions.HttpServerIsRunningException;

public class HTTPServer {
    private final ExecutorService pool = Executors.newFixedThreadPool(2);
    private volatile ServerSocket serverSocket;
    private volatile boolean isRunning;
    static HTTPServerConf httpServerConf;

    public HTTPServer() throws IOException {
        this(CONFIG_FILE);
    }

    HTTPServer(File pathToConfigFile) throws IOException {
        validatePath(pathToConfigFile);
        httpServerConf = ConfigSerializer.getConfig(pathToConfigFile);
    }

    void start() throws IOException {
        runHttpServer();

        while (isRunning()) {
            Socket socket = serverSocket.accept();
            pool.execute(() -> {
                try (PrintWriter sout = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                     BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    System.out.println("Client has been connected");

                    while (isRunning()) {
                        HTTPCreateRequest request = new HTTPCreateRequest(sout, sin);

                        HttpURLConnection connection = (HttpURLConnection) new URL(request.getURL()).openConnection();

                        connection.setRequestMethod(request.getRequestMethod());

                        if ( connection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                            createResponse(connection);
                        }

                        sout.println(getResponse());
                        sout.flush();

                        connection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeSocket(socket);
                }
            });
        }
    }

    private void runHttpServer() throws IOException {
        if ( isRunning() ) {
            throw new HttpServerIsRunningException("Server is already running");
        }

        isRunning = true;
        serverSocket = new ServerSocket(httpServerConf.getPort());

        System.out.println("HTTP sever has been started");
    }

    private void stopHttpServer() throws IOException {
        if (isRunning()) {
            isRunning = false;
            serverSocket.close();
            pool.shutdown();

            System.out.println("HTTP Server has been closed");
        }
    }

    private boolean isRunning() {
        return isRunning;
    }

    HTTPServerConf getHttpServerConf() {
        return httpServerConf;
    }

    @Override
    public String toString() {
        return getHttpServerConf() + getRequest() + getResponse();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        File configFile = validateIsNotEmpty(new File(args[0]));

        HTTPServer httpServer = new HTTPServer(configFile);

        startServer(httpServer);
        httpServer.stopHttpServer();
    }
}
