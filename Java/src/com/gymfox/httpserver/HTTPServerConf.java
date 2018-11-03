package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPServerUtils.validatePath;
import static com.gymfox.httpserver.HTTPServerUtils.validatePort;

public class HTTPServerConf implements HTTPTransformerConfig {
    private final IPv4Address configAddress;
    private final int configPort;
    private final File configRootDirectory;
    private final int configPoolSize;
    private final File configMimeTypes;
    private String configFileStringRepresentation;

    public HTTPServerConf(IPv4Address address, int port, File root_dir, int poolSize, File mimeTypes) throws IOException {
        validatePort(port);
        validatePath(root_dir);

        this.configAddress = address;
        this.configPort = port;
        this.configRootDirectory = root_dir;
        this.configPoolSize = poolSize;
        this.configMimeTypes = mimeTypes;
    }

    public String getAddress() {
        return configAddress.getIpString();
    }

    public int getPort() {
        return configPort;
    }

    public File getRootDirectory() {
        return configRootDirectory;
    }

    public int getPoolSize() {
        return configPoolSize;
    }

    public File getConfigMimeTypes() {
        return configMimeTypes;
    }

    @Override
    public String getConfigHost() {
        return getRootDirectory().getName();
    }

    public String HTTPServerConfToString() {
        return "Configuration file:\n" +
                "\taddress " + getAddress() + "\n" +
                "\tport " + getPort() + "\n" +
                "\troot_dir " + getRootDirectory() + "\n" +
                "\tpool_size " + getPoolSize() + "\n" +
                "\tmime_types " + getConfigMimeTypes();
    }

    @Override
    public String toString() {
        return configFileStringRepresentation == null ? configFileStringRepresentation = HTTPServerConfToString() :
                configFileStringRepresentation;
    }
}
