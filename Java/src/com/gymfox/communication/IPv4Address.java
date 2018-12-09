package com.gymfox.communication;

import static com.gymfox.communication.Exceptions.*;

public class IPv4Address {
    private final String ipAsString;
    private final long ipAsLong;
    static final long LONG_MAX_IP = 4294967295L;
    static final int MAX_OCTETS_VALUE = 255;
    static final int MIN_OCTETS_VALUE = 0;
    static final int OCTETS_COUNT = 4;


    public IPv4Address(String address) throws IllegalArgumentException {
        this.ipAsLong = ipToLong(address);
        this.ipAsString = address;
    }

    public IPv4Address(long address) throws IllegalArgumentException {
        this.ipAsString = ipToString(address);
        this.ipAsLong = address;
    }

    static String ipToString(long ipAsLong) {
        validateLongValue(ipAsLong);
        long[] octets = new long[4];

        octets[0] = ( ipAsLong & 0xFF000000L ) >> 24;
        octets[1] = ( ipAsLong & 0x00FF0000L ) >> 16;
        octets[2] = ( ipAsLong & 0x0000FF00L ) >> 8;
        octets[3] = ( ipAsLong & 0x000000FFL );

        return String.format("%d.%d.%d.%d", octets[0], octets[1], octets[2], octets[3]);
    }

    static long ipToLong(String address) {
        String[] octets = address.split("\\.");

        validateOctetsCount(octets);

        long newIpAsLong = 0;

        for ( String s : octets ) {
            long octetValue = Long.parseLong(s);

            validateOctetValue(octetValue);
            newIpAsLong = newIpAsLong * 256 + octetValue;
        }

        return newIpAsLong;
    }

    static void validateOctetsCount(String[] splitOctets) throws InvalidOctetsCountException {
        if ( splitOctets.length != OCTETS_COUNT ) {
            throw new InvalidOctetsCountException(String.format("%d octets are expected, but found %d",
                    OCTETS_COUNT, splitOctets.length));
        }
    }

    static void validateOctetValue(long octetsNumbers) throws InvalidValueInOctetsException {
        if ( octetsNumbers < MIN_OCTETS_VALUE || octetsNumbers > MAX_OCTETS_VALUE ) {
            throw new InvalidValueInOctetsException(String.format("%d octet is incorrect. Octets numbers should be in" +
                    " range from %d to %d.", octetsNumbers, MIN_OCTETS_VALUE, MAX_OCTETS_VALUE));
        }
    }

    static void validateLongValue(long address) throws InvalidValueInOctetsException {
        if ( address > LONG_MAX_IP || address < 0 ) {
            throw new InvalidValueInOctetsException(String.format("Expected address value in the range 0 to %d, but " +
                    "got %d.", LONG_MAX_IP, address));
        }
    }

    @Override
    public boolean equals(Object otherObject) {
        if ( this == otherObject) return true;
        if ( otherObject == null ) return false;
        if ( getClass() != otherObject.getClass() ) return false;

        IPv4Address other = (IPv4Address) otherObject;
        return ipAsString.equals(other.ipAsString) && ipAsLong == other.ipAsLong;
    }

    @Override
    public String toString() {
        return getIpString() + " = " + getIpLong();
    }

    public String getIpString() {
        return ipAsString;
    }

    public long getIpLong() {
        return ipAsLong;
    }
}
