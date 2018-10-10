package com.gymfox.httpserver;

import java.util.Map;

public class HTTPMimeTypes {
    private Map<String, String> mimeTypes;

    public HTTPMimeTypes(Map<String, String> mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    @Override
    public String toString() {
        return mimeTypes.toString();
    }

    public String getMimeFormat(String file) {
        return mimeTypes.get(getFileExtension(file));
    }

    public static String getFileExtension(String fileName) {
        String[] fileExtension = fileName.split("\\.");

        return fileExtension[fileExtension.length - 1];
    }
}
