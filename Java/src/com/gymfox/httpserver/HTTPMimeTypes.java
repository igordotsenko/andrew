package com.gymfox.httpserver;

import java.util.Map;

import static com.gymfox.httpserver.HTTPServerExceptions.FileNameIsNullOrEmptyException;
import static com.gymfox.httpserver.HTTPServerUtils.validateFileName;

public final class HTTPMimeTypes {
    /**
     * This is map for keeping extension and types.
     */
    private Map<String, String> mimeTypes;

    public HTTPMimeTypes(Map<String, String> mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    @Override
    public String toString() {
        return mimeTypes.toString();
    }

    public String getMimeFormat(String fileName) throws FileNameIsNullOrEmptyException {
        return mimeTypes.getOrDefault(getFileExtension(fileName), "application/octet-stream");
    }

    private String getFileExtension(String fileName) throws FileNameIsNullOrEmptyException {
        validateFileName(fileName);

        String[] fileExtension = fileName.split("\\.");

        return fileExtension[fileExtension.length - 1];
    }
}
