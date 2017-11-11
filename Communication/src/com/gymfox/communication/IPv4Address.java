
package com.gymfox.communication;

public class IPv4Address {
    protected static final long LONG_MAX_IP = 4294967295l;
    protected static final int MAX_OCTETS_VALUE = 255;
    private final int MIN_OCTETS_VALUE = 0;
    private final int OCTETS = 4;

    private String ipAsString;
    private long ipAsLong;
    private boolean isString = false;

    public static class InvalidOctetsCountException extends Exception{};
    public static class InvalidValueInOctetsException extends Exception{};

    public IPv4Address(String address) throws IllegalArgumentException,
            InvalidValueInOctetsException, InvalidOctetsCountException {
        validate(address);
        this.ipAsString = address;
        this.isString = true;
    }

    public IPv4Address(long address) throws IllegalArgumentException {
        validate(address);
        this.ipAsLong = address;
    }

    private void validate(String address) throws IllegalArgumentException,
            InvalidOctetsCountException, InvalidValueInOctetsException {
        String[] octets = address.split("\\.");

        if ( octets.length != OCTETS) {
            throw new InvalidOctetsCountException();
        }

        for ( String eachOctets : octets ) {
            int octetsNumbers = Integer.parseInt(eachOctets);

            if ( octetsNumbers < MIN_OCTETS_VALUE || octetsNumbers > MAX_OCTETS_VALUE ) {
                throw new InvalidValueInOctetsException();
            }
        }
    }

    private void validate(long address) {
        if ( address > LONG_MAX_IP || address < 0 ) {
            throw new IllegalArgumentException();
        }
    }

    public boolean lessThan(IPv4Address address) {
        return this.toLong() < address.toLong();
    }

    public boolean greaterThan(IPv4Address address) {
        return this.toLong() > address.toLong();
    }

    public boolean equals(IPv4Address address) {
        return this.toLong() == address.toLong();
    }

    public String toString() {
        if ( !isString ) {
            StringBuffer out = new StringBuffer();

            for ( int i = 3; i >= 0; i-- ) {
                int shift = i * 8;

                out.append((ipAsLong >> shift) & MAX_OCTETS_VALUE);
                if ( i > 0 ) {
                    out.append(".");
                }
            }
            return out.toString();
        }
        return ipAsString;
    }

    public long toLong() {
        if (isString) {
            String[] octets = ipAsString.split("\\.");
            long newIpAsLong = 0;

            for ( String s : octets ) {
                newIpAsLong = newIpAsLong * 256 + Integer.parseInt(s);
            }
            return newIpAsLong;
        }
        return ipAsLong;
    }
}
