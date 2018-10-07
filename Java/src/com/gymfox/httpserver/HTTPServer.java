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
import static com.gymfox.httpserver.HTTPServerUtils.*;

public class HTTPServer {
    private final ExecutorService pool = Executors.newFixedThreadPool(2);
    private final String httpServerConfAsString;
    private final String mimeTypeFileAsString;
    private volatile ServerSocket serverSocket;
    private volatile boolean isRunning;

    static HTTPServerConf httpServerConf;
    static HTTPMimeTypes mimeTypeFile;

    public HTTPServer() throws IOException {
        this(CONFIG_FILE, MIME_TYPES);
    }

    HTTPServer(File pathToConfigFile, File pathToMimeTypeFile) throws IOException {
        httpServerConf = ConfigSerializer.getHTTPConfig(pathToConfigFile);
        mimeTypeFile = ConfigSerializer.getMimeTypes(pathToMimeTypeFile);

        this.httpServerConfAsString = httpServerConf.HTTPServerConfToString();
        this.mimeTypeFileAsString = mimeTypeFile.extensionToString();
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
                        HTTPTransformer httpTransformer = new HTTPTransformer();
                        HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler();

                        HTTPRequest request = httpTransformer.readHTTPRequest(sin);
                        HTTPResponse response = httpRequestHandler.handleRequest(request);

                        httpTransformer.writeHTTPResponse(response, sout);
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

    String getHttpServerConf() {
        return httpServerConfAsString;
    }

    public String getMimeType() {
        return mimeTypeFileAsString;
    }

    @Override
    public String toString() {
        return getHttpServerConf() + "\n" + getMimeType();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        validateArgumentsCount(args);

        File configFile = checkArguments(new File(args[0]));
        File mimeTypeFile = checkArguments(new File(args[1]));

        HTTPServer httpServer = new HTTPServer(configFile, mimeTypeFile);

        startServer(httpServer);
        httpServer.stopHttpServer();
    }
}
