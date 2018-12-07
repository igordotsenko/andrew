package com.gymfox.httpserver;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.nio.file.NoSuchFileException;

class HTTPServerExceptions {
    static class InvalidArgumentsCountException extends IOException {
        public InvalidArgumentsCountException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class FileIsEmptyException extends NoSuchFileException {
        FileIsEmptyException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class InvalidPortException extends SocketException {
        InvalidPortException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class InvalidHTTPVersionException extends ProtocolException {
        InvalidHTTPVersionException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class InvalidPartsHTTPVersionException extends ProtocolException {
        InvalidPartsHTTPVersionException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class HttpServerIsRunningException extends SocketException {
        HttpServerIsRunningException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class MethodIsNotAllowedException extends Exception {
        public MethodIsNotAllowedException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class NotFoundException extends Exception {
        public NotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class FileNotFoundException extends NoSuchFileException {
        public FileNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class MalformedRequestException extends Throwable {
        public MalformedRequestException(String errorMessage) {
            super(errorMessage);
        }
    }
}
