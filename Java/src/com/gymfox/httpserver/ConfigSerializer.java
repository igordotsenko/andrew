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
    private static int VALID_LINES_COUNT = 2;
    private ConfigSerializer() {}

    static HTTPServerConf getHTTPConfig(File path) throws IOException {
        Map<String, String> config;

        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            config = stream
                    .map(lines -> lines.split("[\\s]++"))
                    .peek(ConfigSerializer::validateConfigFileLines)
                    .collect(Collectors.toMap(lines->lines[0], lines->lines[1]));
        }

        return new HTTPServerConf(new IPv4Address(config.get("address")),
                Integer.parseInt(config.get("port")), new File(config.get("root_dir")));
    }

    static HTTPMimeTypes getMimeTypes(File path) throws IOException {
        Map<String, String> mimeTypes;

        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            mimeTypes = stream
                    .map(lines -> lines.split("[\\s]++"))
                    .collect(Collectors.toMap(lines->lines[0], lines->lines[1]));
        }

        return new HTTPMimeTypes(mimeTypes);
    }

    private static void validateConfigFileLines(String[] lines) {
        if ( lines.length != VALID_LINES_COUNT ) {
            throw new RuntimeException(String.format("Invalid parameters count. Expected %d, but found %d",
                    VALID_LINES_COUNT, lines.length));
        }
    }
}
