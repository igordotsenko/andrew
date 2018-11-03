package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class ConfigSerializer {
    private final static int VALID_LINES_COUNT = 2;

    private ConfigSerializer() {}

    static HTTPServerConf getHTTPConfig(File path) throws IOException {
        Map<String, String> config;

        try (Stream<String> lines = Files.lines(Paths.get(String.valueOf(path)))) {
            config = readFile(lines);
        }

        return new HTTPServerConf(new IPv4Address(config.getOrDefault("address", "127.0.0.1")),
                Integer.parseInt(config.getOrDefault("port", String.valueOf(80))),
                new File(config.getOrDefault("root_dir", "/var/www/localhost/")),
                Integer.parseInt(config.getOrDefault("pool_size", String.valueOf(2))),
                new File(config.getOrDefault("mime_types", "/mime.types")));
    }

    static HTTPMimeTypes getMimeTypes(File path) throws IOException {
        Map<String, String> mimeTypes;

        try (Stream<String> lines = Files.lines(Paths.get(String.valueOf(path)))) {
            mimeTypes = readFile(lines);
        }

        return new HTTPMimeTypes(mimeTypes);
    }

    private static Map<String, String> readFile(Stream<String> lines) {
            return lines
                    .map(line -> line.split("[\\s]{2,}"))
                    .peek(ConfigSerializer::validateConfigFileLine)
                    .collect(Collectors.toMap(line->line[0], line->line[1]));

    }

    private static void validateConfigFileLine(String[] lines) {
        if ( lines.length != VALID_LINES_COUNT ) {
            throw new RuntimeException(String.format("Invalid parameters count. Expected %d, but found %d.",
                    VALID_LINES_COUNT, lines.length));
        }
    }
}
