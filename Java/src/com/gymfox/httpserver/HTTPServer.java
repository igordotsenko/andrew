package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
    private static HttpServerConf httpServerConf;

    private static class HttpServerConf {
        private final IPv4Address address;
        private final int port;
        private final File directory;

        public HttpServerConf(String pathToFile) throws IOException {
            validatePath(new File(pathToFile));
            config = getConfig(pathToFile);

            this.address = new IPv4Address(config.get("address"));
            this.port = validatePort(Integer.parseInt(config.get("port")));
            this.directory = validatePath(new File(config.get("root_dir")));
        }

        private Map<String, String> getConfig(String path) throws IOException {
            FileReader fileReader = new FileReader(path);
            Scanner fileScanner = new Scanner(fileReader);

            Map<String, String> config = new HashMap<>();

            while ( fileScanner.hasNextLine() ) {
                config.put(fileScanner.next(), fileScanner.next());
            }

            fileReader.close();

            return config;
        }

        public String getAddress() {
            return address.getIpString();
        }

        public int getPort() {
            return port;
        }

        public File getRootDirectory() {
            return directory;
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder();

            out.append("Configuration file:\n\t");
            out.append("address     ").append(getAddress()).append("\n\t");
            out.append("port        ").append(getPort()).append("\n\t");
            out.append("root_dir    ").append(getRootDirectory()).append("\n\n");

            return out.toString();
        }
    }

    public HTTPServer() throws IOException {
        this(CONFIG_FILE);
    }

    public HTTPServer(String pathToConfigFile) throws IOException {
        httpServerConf = new HttpServerConf(pathToConfigFile);
        this.isRunning = false;
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

    public boolean isRunning() {
        return isRunning;
    }

    public HttpServerConf getHttpServerConf() {
        return httpServerConf;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        out.append(getHttpServerConf());

        out.append("URL:\n\t");
        out.append(getURL());
        out.append(getRequest());
        out.append(getResponse());

        return out.toString();
    }
}

class StartHttpServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        HTTPServer httpServer = new HTTPServer();

        startServer(httpServer);
        httpServer.stopHttpServer();
        System.out.println(httpServer.toString());

        System.exit(0);
    }
}
