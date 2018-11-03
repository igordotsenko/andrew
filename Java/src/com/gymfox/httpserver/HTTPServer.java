package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gymfox.httpserver.HTTPServerExceptions.HttpServerIsRunningException;
import static com.gymfox.httpserver.HTTPServerExceptions.InvalidRequestParametersCountException;
import static com.gymfox.httpserver.HTTPServerUtils.*;

public class HTTPServer {
    private final ExecutorService pool;
    private final HTTPServerConf httpServerConf;
    static HTTPMimeTypes mimeTypeFile;

    private volatile ServerSocket serverSocket;
    private volatile boolean isRunning;

    public HTTPServer() throws IOException {
        this(CONFIG_FILE);
    }

    HTTPServer(File pathToConfigFile) throws IOException {
        httpServerConf = ConfigSerializer.getHTTPConfig(pathToConfigFile);
        mimeTypeFile = ConfigSerializer.getMimeTypes(httpServerConf.getConfigMimeTypes());
        pool = Executors.newFixedThreadPool(httpServerConf.getPoolSize());
    }

    public void start() throws IOException {
        runHttpServer();

        while (isRunning()) {
            Socket socket = serverSocket.accept();
            pool.execute(() -> {
                try (PrintWriter sout = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                     BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    System.out.println("Client has been connected");

                    if (isRunning()) {
                        HTTPTransformer httpTransformer = new HTTPTransformer();

                        HTTPRequest request = httpTransformer.readHTTPRequest(sin, httpServerConf);
                        HTTPResponse response = new HTTPRequestHandler().handleRequest(request);

                        httpTransformer.writeHTTPResponse(response, sout);
                    }
                } catch (IOException | InvalidRequestParametersCountException e) {
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

    public void stopHttpServer() throws IOException {
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

    public HTTPServerConf getHttpServerConf() {
        return httpServerConf;
    }

    String getHTTPServerConfAsString() {
        return httpServerConf.toString();
    }

    public String getMimeTypeAsString() {
        return mimeTypeFile.toString();
    }

    @Override
    public String toString() {
        return getHTTPServerConfAsString() + "\n" + getMimeTypeAsString();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        validateArgumentsCount(args);

        File configFile = checkArguments(new File(args[0]));

        HTTPServer httpServer = new HTTPServer(configFile);

        startServer(httpServer);
        httpServer.stopHttpServer();
    }
}
