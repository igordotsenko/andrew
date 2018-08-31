package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gymfox.httpserver.HTTPServerUtils.InvalidPortException;
import static com.gymfox.httpserver.HTTPServerUtils.validatePath;

final class ConfigSerializer {
    private ConfigSerializer() {}

    static HTTPServerConf getConfig(File path) throws IOException, InvalidPortException {
        validatePath(path);

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

    private static void validateConfigFileLines(String[] lines) {
        if ( lines.length != 2 ) {
            throw new RuntimeException("Invalid parameters count.");
        }
    }
}