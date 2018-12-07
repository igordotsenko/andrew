package com.gymfox.httpserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gymfox.httpserver.HTTPRequestHandler.CodeResponse.BAD_REQUEST_CODE;
import static com.gymfox.httpserver.HTTPServerExceptions.*;
import static com.gymfox.httpserver.HTTPServerExceptions.FileIsEmptyException;
import static com.gymfox.httpserver.HTTPServerExceptions.HttpServerIsRunningException;
import static com.gymfox.httpserver.HTTPServerExceptions.InvalidArgumentsCountException;
import static com.gymfox.httpserver.HTTPServerUtils.CONFIG_FILE;
import static com.gymfox.httpserver.HTTPServerUtils.closeSocket;
import static com.gymfox.httpserver.HTTPServerUtils.startServer;
import static com.gymfox.httpserver.HTTPServerUtils.validatePath;

public class HTTPServer {
    private static final int ARGUMENTS_COUNT = 1;
    private static final HTTPResponse badResponse =
            new HTTPResponse.ResponseBuilder().addStatusCode(BAD_REQUEST_CODE.getCodeStatus() + " " + BAD_REQUEST_CODE.getCodeName()).build();
    private final ExecutorService threadExecutor;
    private final HTTPServerConf httpServerConf;
    private final HTTPTransformer httpTransformer;
    private final HTTPRequestHandler requestHandler;
    private volatile ServerSocket serverSocket;
    private volatile boolean isRunning;

    public HTTPServer() throws IOException {
        this(CONFIG_FILE);
    }

    public HTTPServer(File pathToConfigFile) throws IOException {
        httpServerConf = ConfigSerializer.getHTTPConfig(pathToConfigFile);
        threadExecutor = Executors.newFixedThreadPool(httpServerConf.getPoolSize());
        httpTransformer = new HTTPTransformer(httpServerConf);
        requestHandler = new HTTPRequestHandler(httpServerConf);
    }

    public void start() throws IOException {
        runHttpServer();

        while (isRunning()) {
            Socket socket = serverSocket.accept();
            threadExecutor.execute(() -> {
                try (PrintWriter sout = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                     BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    System.out.println("Client has been connected");

                    if (isRunning()) {
                        try {
                            HTTPRequest request = httpTransformer.readHTTPRequest(sin);
                            HTTPResponse response = requestHandler.handleRequest(request);
                            httpTransformer.writeHTTPResponse(response, sout);
                        } catch (MalformedRequestException e) {
                            httpTransformer.writeHTTPResponse(badResponse, sout);
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeSocket(socket);
                }
            });
        }
    }

    private Optional<HTTPRequest> readRequest(BufferedReader sin) throws IOException {
        try {
            return Optional.of(httpTransformer.readHTTPRequest(sin));
        } catch (MalformedRequestException e) {
            e.printStackTrace();

            return Optional.empty();
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
            threadExecutor.shutdown();

            System.out.println("HTTP Server has been closed");
        }
    }

    private boolean isRunning() {
        return isRunning;
    }

    public HTTPServerConf getHttpServerConf() {
        return httpServerConf;
    }

    public String getHTTPServerConfAsString() {
        return httpServerConf.toString();
    }

    @Override
    public String toString() {
        return getHTTPServerConfAsString() + "\n";
    }

    static File checkArguments(File inputArgumentFile) throws IOException {
        validatePath(inputArgumentFile);
        validateIsNotEmpty(inputArgumentFile);

        return inputArgumentFile;
    }

    static void validateIsNotEmpty(File configFile) throws FileIsEmptyException {
        if ( configFile.length() == 0 ) {
            throw new FileIsEmptyException("File doesn't have any parameters.");
        }
    }

    static void validateArgumentsCount(String[] args) throws InvalidArgumentsCountException {
        if ( args.length != ARGUMENTS_COUNT ) {
            throw new InvalidArgumentsCountException(String.format("Invalid arguments counts. Expected %d, but found " +
                    "%d.", ARGUMENTS_COUNT, args.length));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        validateArgumentsCount(args);

        File configFile = checkArguments(new File(args[0]));

        HTTPServer httpServer = new HTTPServer(configFile);

        startServer(httpServer);
        httpServer.stopHttpServer();
    }
}
