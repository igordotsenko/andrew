package com.gymfox.httpserver;

import com.gymfox.communication.IPv4Address;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.gymfox.httpserver.HTTPServerUtils.*;
import static com.gymfox.httpserver.HTTPServerUtils.validatePort;

final class ConfigSerializer {
    private ConfigSerializer() {}

    static HTTPServerConf getConfig(File path) throws IOException, InvalidPathToCurrentFileException,
            InvalidPortException {
        validatePath(path);
        FileReader fileReader = new FileReader(path);
        Scanner fileScanner = new Scanner(fileReader);
        Map<String, String> config = new HashMap<>();

        while (fileScanner.hasNextLine()) {
            config.put(fileScanner.next(), fileScanner.next());
        }

        fileReader.close();

        return new HTTPServerConf(new IPv4Address(config.get("address")),
                validatePort(Integer.parseInt(config.get("port"))), validatePath(new File(config.get("root_dir"))));
    }
}