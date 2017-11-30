
package com.gymfox.communication;

public class IPv4Address {
    protected static final long LONG_MAX_IP = 4294967295l;
    protected static final int MAX_OCTETS_VALUE = 255;
    private final int MIN_OCTETS_VALUE = 0;
    private final int OCTETS = 4;

    private String ipAsString;
    private long ipAsLong;

    public static class InvalidOctetsCountException extends Exception{};
    public static class InvalidValueInOctetsException extends Exception{};

    public IPv4Address(String address) throws IllegalArgumentException,
            InvalidValueInOctetsException, InvalidOctetsCountException {
        validate(address);
        this.ipAsString = address;
        this.ipAsLong = ipToLong();
    }

    public IPv4Address(long address) throws IllegalArgumentException {
        validate(address);
        this.ipAsLong = address;
        this.ipAsString = ipToString();
    }

    private void validate(String address) throws IllegalArgumentException,
            InvalidOctetsCountException, InvalidValueInOctetsException {
        String octets[] = address.split("\\.");

        if ( octets.length != OCTETS) {
            throw new InvalidOctetsCountException();
        }

        for ( String octet : octets ) {
            long octetsNumbers = Long.parseLong(octet);

            if ( octetsNumbers < MIN_OCTETS_VALUE || octetsNumbers > MAX_OCTETS_VALUE ) {
                throw new InvalidValueInOctetsException();
            }
        }
    }

    private String ipToString() {
        long[] octets = new long[4];

        octets[0] = ( ipAsLong & 0xFF000000L ) >> 24;
        octets[1] = ( ipAsLong & 0x00FF0000L ) >> 16;
        octets[2] = ( ipAsLong & 0x0000FF00L ) >> 8;
        octets[3] = ( ipAsLong & 0x000000FFL );

        return ipAsString = String.format("%d.%d.%d.%d", octets[0], octets[1], octets[2], octets[3]);
    }

    private long ipToLong() {
        String[] octets = ipAsString.split("\\.");
        long ipAsLong = 0;

        for ( String s : octets ) {
            ipAsLong = ipAsLong * 256 + Long.parseLong(s);
        }

        return ipAsLong;
    }

    private void validate(long address) {
        if ( address > LONG_MAX_IP || address < 0 ) {
            throw new IllegalArgumentException();
        }
    }

    public boolean lessThan(IPv4Address address) {
        return this.getIpLong() < address.getIpLong();
    }

    public boolean greaterThan(IPv4Address address) {
        return this.getIpLong() > address.getIpLong();
    }

    public boolean equals(IPv4Address address) {
        return this.getIpLong() == address.getIpLong();
    }

    public String toString() {
        return ipAsString;
    }

    public long getIpLong() {
        return ipAsLong;
    }
}
