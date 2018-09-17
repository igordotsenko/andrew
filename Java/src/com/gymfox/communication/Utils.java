package com.gymfox.communication;

import com.gymfox.communication.Exceptions.InvalidOctetsCountException;

import static com.gymfox.communication.Exceptions.*;

public class Utils {
    static final long LONG_MAX_IP = 4294967295L;
    static final int MAX_OCTETS_VALUE = 255;
    static final int MIN_OCTETS_VALUE = 0;
    static final int OCTETS_COUNT = 4;

    private Utils() {}

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
            throw new InvalidValueInOctetsException(String.format("%d octet is incorrect.", octetsNumbers));
        }
    }

    static void validateLongValue(long address) throws InvalidValueInOctetsException {
        if ( address > LONG_MAX_IP || address < 0 ) {
            throw new InvalidValueInOctetsException(String.format("Expected address value in the range 0 to %d, but " +
                            "got %d.", LONG_MAX_IP, address));
        }
    }
}
