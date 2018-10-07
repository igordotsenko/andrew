package com.gymfox.httpserver;

import java.util.Map;

public class HTTPMimeTypes {
    private Map<String, String> mimeTypes;

    public HTTPMimeTypes(Map<String, String> mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    public String extensionToString() {
        return String.valueOf(mimeTypes);
    }

    public String getMimeFormat(String file) {
        return mimeTypes.get(getFileExtension(file));
    }

    public static String getFileExtension(String file) {
        String[] fileExtension = file.split("\\.");

        return fileExtension[fileExtension.length - 1];
    }
}
