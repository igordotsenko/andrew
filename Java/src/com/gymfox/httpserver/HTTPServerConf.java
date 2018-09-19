package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPServerUtils.validatePath;
import static com.gymfox.httpserver.HTTPServerUtils.validatePort;
import static com.gymfox.httpserver.HTTPServer.httpServerConf;

public class HTTPServerConf {
    private final IPv4Address configAddress;
    private final int configPort;
    private final File configRootDirectory;

    public HTTPServerConf(IPv4Address address, int port, File root_dir) throws IOException {
        validatePort(port);
        validatePath(root_dir);

        this.configAddress = address;
        this.configPort = port;
        this.configRootDirectory = root_dir;
    }

    String getAddress() {
        return configAddress.getIpString();
    }

    int getPort() {
        return configPort;
    }

    File getRootDirectory() {
        return configRootDirectory;
    }

    static String getHost() {
        return httpServerConf.getRootDirectory().getName();
    }

    @Override
    public String toString() {
        return "Configuration file:\n" +
                "\taddress " + getAddress() + "\n" +
                "\tport " + getPort() + "\n" +
                "\troot_dir " + getRootDirectory() + "\n";
    }
}
