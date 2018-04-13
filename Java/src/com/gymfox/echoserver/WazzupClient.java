package com.gymfox.echoserver;

import com.gymfox.communication.IPv4Address;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.jndi.ldap.LdapCtx.DEFAULT_PORT;

public class WazzupClient {
    private final static int MAX_PERMISSION_PORT = 65536;
    private final static int MIN_PERMISSION_PORT = 1024;
    private Socket socket;
    private IPv4Address address;
    private int port;
    private volatile boolean isConnect;
    private ExecutorService pool = Executors.newFixedThreadPool(1);

    public static class InvalidPortException extends Exception {
        public InvalidPortException(String errorMessage) {
            super(errorMessage);
        }
    }

    public WazzupClient() throws InvalidPortException {
        this(new IPv4Address("127.0.0.1"), DEFAULT_PORT);
        this.isConnect = false;
    }

    public WazzupClient(IPv4Address address, int port) throws InvalidPortException {
        validate(port);
        this.address = address;
        this.port = port;
    }

    private void validate(int port) throws InvalidPortException {
        if ( port < MIN_PERMISSION_PORT || port > MAX_PERMISSION_PORT ) {
            throw new InvalidPortException(String.format("%d is invalid port. Value beetwen %d and %d is expected.",
                    port, MIN_PERMISSION_PORT, MAX_PERMISSION_PORT));
        }
    }

    public String getAddress() {
        return address.toString();
    }

    public int getPort() {
        return port;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void connect() throws IOException {
        startConnection();
        System.out.println("Send your message");

        while (isConnect()) {
            pool.execute(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                     BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {

                    String line;

                    while (checkLine(line = keyboard.readLine())) {
                        out.println(line);
                        out.flush();
                        line = in.readLine();
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    endConnection(socket);
                }
            });
        }
    }

    private void startConnection() throws IOException {
        if (!isConnect()) {
            socket = new Socket(getAddress(), getPort());
            isConnect = true;
            System.out.println("Connection successful");
        }
    }

    private void endConnection(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkLine(String line) {
        return isConnect() && line != null;
    }

    public void stop() {
        pool.shutdown();
        System.out.println("Client has benn disconnected");
    }
}

class StartWazzupClient {
    public static void main(String[] args) throws WazzupClient.InvalidPortException, InterruptedException {
        WazzupClient client = new WazzupClient(new IPv4Address("127.0.0.129"), 8080);

        connectToServer(client);
        client.stop();

        System.exit(0);
    }

    public static void connectToServer(WazzupClient client) throws InterruptedException {
        Runnable r = () -> {
            try {
                client.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        ExecutorService t = Executors.newFixedThreadPool(1);
        t.execute(r);
        Thread.sleep(20000);
    }
}
