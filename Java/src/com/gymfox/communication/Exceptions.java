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

    public static class InvalidMaskValueException extends IllegalArgumentException {
        public InvalidMaskValueException(String errorMesage) {
            super(errorMesage);
        }
    }

    public static class InvalidGatewayException extends IllegalArgumentException {
        public InvalidGatewayException(String errorMessage) {
            super(errorMessage);
        }
    }
}
