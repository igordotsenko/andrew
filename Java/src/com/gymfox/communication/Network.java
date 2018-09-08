package com.gymfox.communication;

import java.util.ArrayList;
import java.util.List;

import static com.gymfox.communication.Utils.*;

public class Network {
    static final List<IPv4Address> privateAddress = new ArrayList<IPv4Address>() {{
        add(new IPv4Address("10.0.0.0"));
        add(new IPv4Address("172.16.0.0"));
        add(new IPv4Address("192.168.0.0"));
    }};

    private static final int RESERVED_ADDRESS = 2;
    private static final int DEFAULT_MASK = 24;
    private final int SUBNETS_COUNT = 2;

    private final int maskLength;
    private final long maskAsLong;
    private final String networkMetadataAsString;
    private final IPv4Address address;
    private final NetworkMetadata networkMetadata;

    private static final class NetworkMetadata {
        IPv4Address broadcastAddress;
        IPv4Address firstUsableAddress;
        IPv4Address lastUsableAddress;
        String netAsString;
        String maskAsString;
        int totalHosts;
        boolean isPublic = true;

        public NetworkMetadata(Network network) {
            setNetAsString(network.getNetworkAddress(), network.getMaskLength());

            setBroadcastAddress(network.getNetworkAddress().getIpLong(), network.getMaskLength());
            setFirstUsableAddress(network.getNetworkAddress().getIpLong());
            setLastUsableAddress();
            setMaskAsString(network.getMaskAsLong());
            setTotalHosts(network.getMaskLength());
            setPublic(network);
        }

        void setNetAsString(IPv4Address networkAddress, int maskAsInt) {
            netAsString = networkAddress.getIpString() + "/" + maskAsInt;
        }

        void setBroadcastAddress(long address, int maskAsInt) {
            broadcastAddress = new IPv4Address(address | (LONG_MAX_IP >> maskAsInt));
        }

        void setFirstUsableAddress(long address) {
            firstUsableAddress = new IPv4Address(address + 1);
        }

        void setLastUsableAddress() {
            lastUsableAddress = new IPv4Address(broadcastAddress.getIpLong() - 1);
        }

        void setMaskAsString(long address) {
            maskAsString = ipToString(address);
        }

        /**
         * Calculate total hosts for this network.
         */
        void setTotalHosts(int maskAsInt) {
            totalHosts = ((1 << (MAX_MASK_VALUE - maskAsInt)) - RESERVED_ADDRESS);
        }

        void setPublic(Network network) {
            isPublic = !privateAddress.contains(network.getNetworkAddress());
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

    public long maskToLong(int maskLength) {
        validateMask(maskLength);

        return LONG_MAX_IP >> maskLength ^ LONG_MAX_IP;
    }

    public boolean contains(IPv4Address address) {
        return address.greaterThan(getNetworkAddress()) && address.lessThan(getBroadcastAddress());
    }

    private String networkToString() {

        return "Network address: " + getNetworkAddress().getIpString() + "\n" +
                "Network broadcast: " + getBroadcastAddress().getIpString() + "\n" +
                "First address: " + getFirstUsableAddress().getIpString() + "\n" +
                "Last address: " + getLastUsableAddress().getIpString() + "\n" +
                "Total hosts: " + getTotalHosts() + "\n" +
                "Is public: " + isPublic() + "\n";
    }

    @Override
    public String toString() {
        return networkMetadataAsString;
    }

    public String getNetworkAsString() {
        return networkMetadata.netAsString;
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
        validateMask(newMaskLength);
        Network[] subnets = new Network[SUBNETS_COUNT];
        long secondSubnetAddress = getFirstUsableAddress().getIpLong() + getTotalHosts() / SUBNETS_COUNT;

        subnets[0] = new Network(new IPv4Address(getFirstUsableAddress().getIpLong()), newMaskLength);
        subnets[1] = new Network(new IPv4Address(secondSubnetAddress), newMaskLength);

        return subnets;
    }
}
