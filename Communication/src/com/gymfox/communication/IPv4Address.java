
package com.gymfox.communication;

public class IPv4Address {
    protected static final long LONG_MAX_IP = 4294967295l;
    protected static final int MAX_OCTETS_VALUE = 255;
    private final int MIN_OCTETS_VALUE = 0;
    private final int OCTETS_COUNT = 4;

    private final String ipAsString;
    private final long ipAsLong;

    public static class InvalidValueInOctetsException extends IllegalArgumentException {
        public InvalidValueInOctetsException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidOctetsCountException extends  IllegalArgumentException {
        public InvalidOctetsCountException(String errorMessage) {
            super(errorMessage);
        }
    }

    public IPv4Address(String address) throws IllegalArgumentException {
        this.ipAsLong = ipToLong(address);
        this.ipAsString = address;
    }

    public IPv4Address(long address) throws IllegalArgumentException {
        this.ipAsString = ipToString(address);
        this.ipAsLong = address;
    }

    private long ipToLong(String address) {
        String[] octets = address.split("\\.");

        validateOctetsCount(octets);

        long newIpAsLong = 0;

        for ( String s : octets) {
            long octetValue = Long.parseLong(s);

            validateOctetValue(octetValue);
            newIpAsLong = newIpAsLong * 256 + octetValue;
        }

        return newIpAsLong;
    }

    private String ipToString(long ipAsLong) {
        validateLongValue(ipAsLong);
        long[] octets = new long[4];

        octets[0] = ( ipAsLong & 0xFF000000L ) >> 24;
        octets[1] = ( ipAsLong & 0x00FF0000L ) >> 16;
        octets[2] = ( ipAsLong & 0x0000FF00L ) >> 8;
        octets[3] = ( ipAsLong & 0x000000FFL );

        return String.format("%d.%d.%d.%d", octets[0], octets[1], octets[2], octets[3]);
    }

    private void validateOctetsCount(String[] splitOctets) {
        if ( splitOctets.length != OCTETS_COUNT ) {
            throw new InvalidOctetsCountException("Invalid octets count");
        }
    }

    private void validateOctetValue(long octetsNumbers) {
        if ( octetsNumbers < MIN_OCTETS_VALUE || octetsNumbers > MAX_OCTETS_VALUE ) {
            throw new InvalidValueInOctetsException("Invalid octet value");
        }
    }

    private void validateLongValue(long address) {
        if ( address > LONG_MAX_IP || address < 0 ) {
            throw new InvalidValueInOctetsException("Invalid long octet value");
        }
    }

    @Override
    public String toString() {
        return getIpString() + " = " + getIpLong();
    }

    public boolean lessThan(IPv4Address address) {
        return this.getIpLong() <= address.getIpLong();
    }

    public boolean greaterThan(IPv4Address address) {
        return this.getIpLong() >= address.getIpLong();
    }

    public boolean equals(IPv4Address address) {
        return this.getIpLong() == address.getIpLong();
    }

    public String getIpString() {
        return ipAsString;
    }

    public long getIpLong() {
        return ipAsLong;
    }
}
