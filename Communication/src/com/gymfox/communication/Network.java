package com.gymfox.communication;

import static com.gymfox.communication.IPv4Address.LONG_MAX_IP;
import static com.gymfox.communication.IPv4Address.MAX_OCTETS_VALUE;

public class Network {
    private final IPv4Address[] privateAddress = {
            new IPv4Address("10.0.0.0"),
            new IPv4Address("172.16.0.0"),
            new IPv4Address("192.168.0.0")
    };

    private final int MAX_MASK_VALUE = 32;
    private final int MIN_MASK_VALUE = 0;
    private final int RESERVED_ADDRESS = 2;
    private final int SUBNETS_COUNT = 2;
    private final static int DEFAULT_MASK = 24;

    private int maskLength;
    private IPv4Address address;

    public static class InvalidMaskValueExcetion extends Exception {}
    public static class InvalidNewMaskLenghtException extends Exception {}

    public Network(IPv4Address address) throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, InvalidMaskValueExcetion {
        this(address, DEFAULT_MASK);
    }

    public Network(IPv4Address address, int maskLength) throws InvalidMaskValueExcetion,
            IPv4Address.InvalidOctetsCountException, IPv4Address.InvalidValueInOctetsException {
        validate(maskLength);
        this.maskLength = maskLength;
        this.address = new IPv4Address(address.toLong() & getMask());
    }

    private void validate(int maskLength) throws InvalidMaskValueExcetion {
        if ( maskLength < MIN_MASK_VALUE || maskLength > MAX_MASK_VALUE ) {
            throw new InvalidMaskValueExcetion();
        }
    }

    public boolean contains(IPv4Address address) {
        return address.greaterThan(getAddress()) && address.lessThan(getBroadcastAddress());
    }

    public IPv4Address getAddress() {
        return address;
    }

    public IPv4Address getBroadcastAddress() {
        return (new IPv4Address(address.toLong() | (LONG_MAX_IP >> maskLength)));
    }

    public IPv4Address getFirstUsableAddress() {
        if ( maskLength < MAX_MASK_VALUE ) {
            return new IPv4Address(address.toLong() + 1);
        }
        return address;
    }

    public IPv4Address getLastUsableAddress() {
        if ( maskLength < MAX_MASK_VALUE ) {
            return new IPv4Address(getBroadcastAddress().toLong() - 1);
        }
        return getBroadcastAddress();
    }

    public long getMask() {
        int shift = MAX_MASK_VALUE - maskLength;

        return (LONG_MAX_IP >> shift) << shift;
    }

    public String getMaskString() {
        StringBuffer out = new StringBuffer();

        for ( int i = 3; i >= 0; i-- ) {
            int shift = i * 8;

            out.append((getMask() >> shift) & MAX_OCTETS_VALUE);
            if ( i > 0 ) {
                out.append(".");
            }
        }
        return out.toString();
    }

    public int getMaskLength() {
        return maskLength;
    }

    public Network[] getSubnets() throws IPv4Address.InvalidValueInOctetsException,
            IPv4Address.InvalidOctetsCountException, InvalidMaskValueExcetion, InvalidNewMaskLenghtException {
        int newMaskLength = maskLength + 1;
        Network[] subnets = new Network[SUBNETS_COUNT];
        long secondSubnetAddress = getFirstUsableAddress().toLong() + getTotalHosts() / SUBNETS_COUNT;

        subnets[0] = new Network(new IPv4Address(getFirstUsableAddress().toLong()), newMaskLength);
        subnets[1] = new Network(new IPv4Address(secondSubnetAddress), newMaskLength);

        return subnets;
    }

    public long getTotalHosts() {
        return ((1 << (MAX_MASK_VALUE - maskLength)) - RESERVED_ADDRESS);
    }

    public boolean isPublic() throws IPv4Address.InvalidOctetsCountException, IPv4Address.InvalidValueInOctetsException {
        for ( IPv4Address pa : privateAddress ) {
            if ( getAddress().equals(pa) ) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return getAddress().toString() + "/" + getMaskLength();
    }
}
