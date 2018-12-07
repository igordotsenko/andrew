package com.gymfox.httpserver;

import java.io.File;

public final class URIUtils {
    private static final String INDEX_HTML = "index.html";

    private URIUtils() {}

    static File processingRequestURI(File rootDir, String inputURI) {
        File requestURI = new File(rootDir + inputURI);

        if ( requestURI.equals(rootDir) ) {
            return new File(requestURI + "/" + INDEX_HTML);
        }

        return requestURI;
    }
}
