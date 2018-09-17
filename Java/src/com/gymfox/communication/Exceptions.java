package com.gymfox.communication;

public class Exceptions {
    public static class InvalidValueInOctetsException extends IllegalArgumentException {
        public InvalidValueInOctetsException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidOctetsCountException extends IllegalArgumentException {
        public InvalidOctetsCountException(String errorMessage) {
            super(errorMessage);
        }
    }
}
