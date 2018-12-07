package com.gymfox.httpserver;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gymfox.httpserver.HTTPServerExceptions.FileNotFoundException;

public final class HTTPMimeTypes {
    private static final String UNKNOWN_EXTENSION = "application/octet-stream";
    private static final String DOT = ".";
    private static final int NEXT_SYMBOL = 1;

    /**
     * This is map for keeping mime types.
     */
    private Map<String, String> mimeTypes;

    public HTTPMimeTypes(Map<String, String> mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    @Override
    public String toString() {
        return mimeTypes.toString();
    }

    public String getMimeFormat(String fileName) throws FileNotFoundException {
        validateFileName(fileName);

        return Stream.of(fileName)
                .map(extension -> mimeTypes.getOrDefault(extension.substring(extension.lastIndexOf(DOT) + NEXT_SYMBOL),
                        UNKNOWN_EXTENSION))
                .collect(Collectors.joining());
    }

    static void validateFileName(String fileName) throws FileNotFoundException {
        if ( fileName == null || fileName.trim().isEmpty() ) {
            throw new FileNotFoundException("File name is empty or null.");
        }
    }
}
