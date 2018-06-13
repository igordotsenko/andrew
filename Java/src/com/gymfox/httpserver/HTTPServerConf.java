package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;

import java.io.File;

public class HTTPServerConf {
    private final IPv4Address configAddress;
    private final int configPort;
    private final File configRootDirectory;

    public HTTPServerConf(IPv4Address address, int port, File root_dir) {
        this.configAddress = address;
        this.configPort = port;
        this.configRootDirectory = root_dir;
    }

    IPv4Address getAddress() {
        return configAddress;
    }

    int getPort() {
        return configPort;
    }

    File getRootDirectory() {
        return configRootDirectory;
    }

    @Override
    public String toString() {

        return "Configuration file:\n" +
                "address     " + getAddress() + "\n" +
                "port        " + getPort() + "\n" +
                "root_dir    " + getRootDirectory() + "\n\n";
    }
}
