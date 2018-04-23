package com.gymfox.echoserver;

import com.gymfox.communication.IPv4Address;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gymfox.echoserver.ServerUtils.*;

public class WazzupClient implements Executable {
    private final static IPv4Address DEFAULT_IP_ADDRESS = new IPv4Address("127.0.0.1");
    private final IPv4Address address;
    private final int port;
    private Socket socket;
    private boolean isConnect;
    private ExecutorService pool = Executors.newFixedThreadPool(1);

    public WazzupClient() throws ServerUtils.InvalidPortException {
        this(DEFAULT_IP_ADDRESS, DEFAULT_PORT);
        this.isConnect = false;

    }

    public WazzupClient(IPv4Address address, int port) throws ServerUtils.InvalidPortException {
        validate(port);
        this.address = address;
        this.port = port;
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

    public void start() throws IOException {
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
                    closeSocket(socket);
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

    public boolean checkLine(String line) {
        return isConnect() && line != null;
    }

    public void stop() {
        if ( isConnect() ) {
            pool.shutdown();
            isConnect = false;
            System.out.println("Client has benn disconnected");
        }
    }
}

class StartWazzupClient {
    public static void main(String[] args) throws InterruptedException, InvalidPortException {
        WazzupClient client = new WazzupClient();

        startConnect(client);

        client.stop();

        System.exit(0);
    }
}
