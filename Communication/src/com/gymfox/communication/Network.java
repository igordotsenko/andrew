package com.gymfox.communication;

import static com.gymfox.communication.IPv4Address.LONG_MAX_IP;

public class Network {
    private static final IPv4Address privateAddress[] = {
            new IPv4Address("10.0.0.0"),
            new IPv4Address("172.16.0.0"),
            new IPv4Address("192.168.0.0")
    };

    private static final int MAX_MASK_VALUE = 32;
    private static final int MIN_MASK_VALUE = 0;
    private static final int RESERVED_ADDRESS = 2;
    private final int SUBNETS_COUNT = 2;
    private final static int DEFAULT_MASK = 24;

    private final int maskLength;
    private final long maskAsLong;
    private final String networkMetadataAsString;
    private final IPv4Address address;
    private final NetworkMetadata networkMetadata;

    public static class InvalidMaskValueException extends IllegalArgumentException{
        public InvalidMaskValueException(String errorMesage) {
            super(errorMesage);
        }
    }

    private static final class NetworkMetadata {
        IPv4Address networkAddress;
        int maskAsInt;

        IPv4Address broadcastAddress;
        IPv4Address firstUsableAddress;
        IPv4Address lastUsableAddress;
        String netAsString;
        String maskAsString;
        int totalHosts;
        boolean isPublic = true;

        public NetworkMetadata(Network address) {
            this.networkAddress = address.getNetworkAddress();
            this.maskAsInt = address.getMaskLength();

            setNetAsString(networkAddress, maskAsInt);

            setBroadcastAddress(address.getNetworkAddress().getIpLong());
            setFirstUsableAddress(address.getNetworkAddress().getIpLong());
            setLastUsableAddress();
            setMaskAsString(address.getMaskAsLong());
            setTotalHosts();
            setPublic(address);
        }

        void setNetAsString(IPv4Address networkAddress, int maskAsInt) {
            netAsString = networkAddress.getIpString() + "/" + maskAsInt;
        }

        void setBroadcastAddress(long address) {
            broadcastAddress = new IPv4Address(address | (LONG_MAX_IP >> maskAsInt));
        }

        void setFirstUsableAddress(long address) {
            firstUsableAddress = new IPv4Address(address + 1);
        }

        void setLastUsableAddress() {
            lastUsableAddress = new IPv4Address(broadcastAddress.getIpLong() - 1);
        }

        void setMaskAsString(long address) {
            long[] octets = new long[4];

            octets[0] = ( address & 0xFF000000L ) >> 24;
            octets[1] = ( address & 0x00FF0000L ) >> 16;
            octets[2] = ( address & 0x0000FF00L ) >> 8;
            octets[3] = ( address & 0x000000FFL ) ;

            maskAsString = String.format("%d.%d.%d.%d", octets[0], octets[1], octets[2], octets[3]);
        }

        void setTotalHosts() {
            totalHosts = ((1 << (MAX_MASK_VALUE - maskAsInt)) - RESERVED_ADDRESS);
        }

        void setPublic(Network address) {
            for ( IPv4Address pa : privateAddress ) {
                if ( address.getNetworkAddress().equals(pa) ) {
                    isPublic = false;
                }
            }
        }
    }

    public Network(IPv4Address address) {
        this(address, DEFAULT_MASK);
    }

    public Network(IPv4Address address, int maskLength) {
        this.maskAsLong = maskToLong(maskLength);
        this.maskLength = maskLength;
        this.address = new IPv4Address(address.getIpLong() & getMaskAsLong());
        this.networkMetadata = new NetworkMetadata(this);

        this.networkMetadataAsString = networkToString();
    }

    private long maskToLong(int maskLength) {
        validate(maskLength);
        int shift = MAX_MASK_VALUE - maskLength;

        return LONG_MAX_IP << shift;
    }

    private void validate(int maskLength) {
        if ( maskLength < MIN_MASK_VALUE || maskLength > MAX_MASK_VALUE ) {
            throw new InvalidMaskValueException("Invalid Mask");
        }
    }

    public boolean contains(IPv4Address address) {
        return address.greaterThan(getNetworkAddress()) && address.lessThan(getBroadcastAddress());
    }

    private String networkToString() {
        StringBuffer out = new StringBuffer();

        out.append("Network: " + toString() + "\n");
        out.append("Network address: " + getNetworkAddress().getIpString() + "\n");
        out.append("Network broadcast: " + getBroadcastAddress().getIpString() + "\n");
        out.append("First address: " + getFirstUsableAddress().getIpString() + "\n");
        out.append("Last address: " + getLastUsableAddress().getIpString() + "\n");
        out.append("Total hosts: " + getTotalHosts() + "\n");
        out.append("Is public: " + isPublic() + "\n");

        return out.toString();
    }

    @Override
    public String toString() {
        return networkMetadata.netAsString;
    }

    public String getNetworkMetadata() {
        return networkMetadataAsString;
    }

    public IPv4Address getNetworkAddress() {
        return address;
    }

    public IPv4Address getBroadcastAddress() {
        return networkMetadata.broadcastAddress;
    }

    public IPv4Address getFirstUsableAddress() {
        return networkMetadata.firstUsableAddress;
    }

    public IPv4Address getLastUsableAddress() {
        return networkMetadata.lastUsableAddress;
    }

    public int getMaskLength() {
        return maskLength;
    }

    public long getMaskAsLong() {
        return maskAsLong;
    }

    public String getMaskAsString() {
        return networkMetadata.maskAsString;
    }

    public long getTotalHosts() {
        return networkMetadata.totalHosts;
    }

    public boolean isPublic() {
        return networkMetadata.isPublic;
    }

    public Network[] getSubnets() {
        int newMaskLength = maskLength + 1;
        validate(newMaskLength);
        Network[] subnets = new Network[SUBNETS_COUNT];
        long secondSubnetAddress = getFirstUsableAddress().getIpLong() + getTotalHosts() / SUBNETS_COUNT;

        subnets[0] = new Network(new IPv4Address(getFirstUsableAddress().getIpLong()), newMaskLength);
        subnets[1] = new Network(new IPv4Address(secondSubnetAddress), newMaskLength);

        return subnets;
    }
}
