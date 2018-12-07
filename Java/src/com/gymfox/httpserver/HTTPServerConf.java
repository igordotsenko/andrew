package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;

import java.io.File;
import java.io.IOException;

import static com.gymfox.httpserver.HTTPServerUtils.validatePath;
import static com.gymfox.httpserver.HTTPServerExceptions.InvalidPortException;

public class HTTPServerConf implements HTTPTransformerConfig {
    private static final int MIN_SYSTEM_PORT_VALUE = 0;
    private static final int MAX_SYSTEM_PORT_VALUE = 65536;
    private final IPv4Address address;
    private final int configPort;
    private final File rootDirectory;
    private final int threadPoolSize;
    private final File supportedMimeTypesConfigurationFile;
    private final HTTPMimeTypes mimeTypes;
    private String configFileStringRepresentation;

    public HTTPServerConf(IPv4Address address, int port, File rootDir, int poolSize,
                          File supportedMimeTypesConfigurationFile) throws IOException {
        validatePort(port);
        validatePath(rootDir);

        this.address = address;
        this.configPort = port;
        this.rootDirectory = rootDir;
        this.threadPoolSize = poolSize;
        this.mimeTypes = ConfigSerializer.getMimeTypes(supportedMimeTypesConfigurationFile);
        this.supportedMimeTypesConfigurationFile = supportedMimeTypesConfigurationFile;
    }

    static void validatePort(int port) throws InvalidPortException {
        if ( port < MIN_SYSTEM_PORT_VALUE || port > MAX_SYSTEM_PORT_VALUE ) {
            throw new InvalidPortException(String.format("%d is invalid port. Value between %d and %d is expected",
                    port, MIN_SYSTEM_PORT_VALUE, MAX_SYSTEM_PORT_VALUE));
        }
    }

    public String getAddress() {
        return address.getIpString();
    }

    public int getPort() {
        return configPort;
    }

    public File getRootDirectory() {
        return rootDirectory;
    }

    public int getPoolSize() {
        return threadPoolSize;
    }

    public File getConfigMimeTypes() {
        return supportedMimeTypesConfigurationFile;
    }

    public HTTPMimeTypes getMimeTypes() {
        return mimeTypes;
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
