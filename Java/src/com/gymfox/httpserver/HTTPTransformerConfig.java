package com.gymfox.httpserver;

import java.io.File;

public interface HTTPTransformerConfig {
    File getRootDirectory();
    String getConfigHost();
    HTTPMimeTypes getMimeTypes();
}
