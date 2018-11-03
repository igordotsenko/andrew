package com.gymfox.httpserver;

import java.io.File;

public final class URIHandler {
    private static final String INDEX_HTML = "index.html";

    private URIHandler() {}

    static File processingRequestURI(File rootDir, String inputURI) {
        File requestURI = new File(rootDir + inputURI);

        if ( requestURI.isDirectory() ) {
            return new File(requestURI + "/" + INDEX_HTML);
        }

        return requestURI;
    }
}
