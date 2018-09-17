package com.gymfox.communication;

import static com.gymfox.communication.Utils.ipToLong;
import static com.gymfox.communication.Utils.ipToString;

public class IPv4Address {
    private final String ipAsString;
    private final long ipAsLong;

    public IPv4Address(String address) throws IllegalArgumentException {
        this.ipAsLong = ipToLong(address);
        this.ipAsString = address;
    }

    public IPv4Address(long address) throws IllegalArgumentException {
        this.ipAsString = ipToString(address);
        this.ipAsLong = address;
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

    public boolean lessThan(IPv4Address address) {
        return this.getIpLong() < address.getIpLong();
    }

    public boolean greaterThan(IPv4Address address) {
        return this.getIpLong() > address.getIpLong();
    }

    public String getIpString() {
        return ipAsString;
    }

    public long getIpLong() {
        return ipAsLong;
    }
}
