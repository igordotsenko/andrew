package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class ConfigSerializer {
    private final static int VALID_LINES_COUNT = 2;
    private final static String DEFAULT_ADDRESS = "127.0.0.1";
    private final static int DEFAULT_PORT = 8080;
    private final static String DEFAULT_ROOT_DIR = "/var/www/localhost/";
    private final static int DEFAULT_POOL_SIZE = 2;
    private final static String DEFAULT_MIME_TYPES= "mime.types";

    private ConfigSerializer() {}

    static HTTPServerConf getHTTPConfig(File configFile) throws IOException {
        Map<String, String> configurationNameToValue = readFile(configFile);

        return new HTTPServerConf(new IPv4Address(configurationNameToValue.getOrDefault("address", DEFAULT_ADDRESS)),
                getIntValueFromConfigFile(configurationNameToValue, "port").orElse(DEFAULT_PORT),
                new File(configurationNameToValue.getOrDefault("root_dir", DEFAULT_ROOT_DIR)),
                getIntValueFromConfigFile(configurationNameToValue, "pool_size").orElse(DEFAULT_POOL_SIZE),
                new File(configurationNameToValue.getOrDefault("mime_types", DEFAULT_MIME_TYPES)));
    }

    static OptionalInt getIntValueFromConfigFile(Map<String, String> configurationNameToValue, String field) {

        return Optional.ofNullable(configurationNameToValue.get(field))
                .map(v -> OptionalInt.of(Integer.parseInt(v)))
                .orElseGet(OptionalInt::empty);
    }

    static HTTPMimeTypes getMimeTypes(File configFile) throws IOException {
        return new HTTPMimeTypes(readFile(configFile));
    }

    private static Map<String, String> readFile(File configFile) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(configFile.getAbsolutePath()))) {

            return lines
                    .map(line -> line.split("[\\s]{2,}"))
                    .peek(ConfigSerializer::validateConfigFileLine)
                    .collect(Collectors.toMap(line -> line[0], line -> line[1]));
        }
    }

    private static void validateConfigFileLine(String[] lines) {
        if ( lines.length != VALID_LINES_COUNT ) {
            throw new RuntimeException(String.format("Invalid parameters count. Expected %d, but found %d.",
                    VALID_LINES_COUNT, lines.length));
        }
    }
}
