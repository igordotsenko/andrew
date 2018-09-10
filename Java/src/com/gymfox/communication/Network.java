package com.gymfox.communication;

import java.util.Arrays;
import java.util.List;

import static com.gymfox.communication.Utils.*;

public class Network {
    private static final List<IPv4Address> privateAddress = Arrays.asList(
            new IPv4Address("10.0.0.0"),
            new IPv4Address("172.16.0.0"),
            new IPv4Address("192.168.0.0")
    );

    private static final int RESERVED_ADDRESS = 2;
    private static final int DEFAULT_MASK = 24;
    private static final int SUBNETS_COUNT = 2;

    private final int maskLength;
    private final long maskAsLong;
    private final IPv4Address networkAddress;
    private final NetworkMetadata networkMetadata;

    private static final class NetworkMetadata {
        IPv4Address broadcastAddress;
        IPv4Address firstUsableAddress;
        IPv4Address lastUsableAddress;
        String netAsString;
        String maskAsString;
        int totalHosts;
        boolean isPublic;
        String networkMetadataAsString;

        public NetworkMetadata(IPv4Address networkAddress, long maskAsLong, int maskLength) {
            setNetAsString(networkAddress, maskLength);
            setBroadcastAddress(networkAddress.getIpLong(), maskLength);
            setFirstUsableAddress(networkAddress.getIpLong());
            setLastUsableAddress();
            setMaskAsString(maskAsLong);
            setTotalHosts(maskLength);
            setPublic(networkAddress);
            this.networkMetadataAsString = networkToString(networkAddress);
        }

        void setNetAsString(IPv4Address networkAddress, int maskAsInt) {
            netAsString = networkAddress.getIpString() + "/" + maskAsInt;
        }

        void setBroadcastAddress(long networkAddress, int maskAsInt) {
            broadcastAddress = new IPv4Address(networkAddress | (LONG_MAX_IP >> maskAsInt));
        }

        void setFirstUsableAddress(long networkAddress) {
            firstUsableAddress = new IPv4Address(networkAddress + 1);
        }

        void setLastUsableAddress() {
            lastUsableAddress = new IPv4Address(broadcastAddress.getIpLong() - 1);
        }

        void setMaskAsString(long networkAddress) {
            maskAsString = ipToString(networkAddress);
        }

        /**
         * Calculate total hosts for this network.
         * First, calculate the difference between MAX_MASK_VALUE nad maskAsInt.
         * Then, use the bitwise left shift operator to get all hosts in this network.
         * And the last, we take away two reserved address to get hosts, that we can use in our network.
         */
        void setTotalHosts(int maskAsInt) {
            totalHosts = ((1 << (MAX_MASK_VALUE - maskAsInt)) - RESERVED_ADDRESS);
        }

        void setPublic(IPv4Address networkAddress) {
            isPublic = !privateAddress.contains(networkAddress);
        }

        String networkToString(IPv4Address networkAddress) {

            return "Network networkAddress: " + networkAddress + "\n" +
                "Network broadcast: " + broadcastAddress.getIpString() + "\n" +
                "First networkAddress: " + firstUsableAddress.getIpString() + "\n" +
                "Last networkAddress: " + lastUsableAddress.getIpString() + "\n" +
                "Total hosts: " + totalHosts + "\n" +
                "Is public: " + isPublic + "\n";
        }
    }

    public Network(IPv4Address address) {
        this(address, DEFAULT_MASK);
    }

    public Network(IPv4Address address, int maskLength) {
        this.maskAsLong = maskToLong(maskLength);
        this.maskLength = maskLength;
        this.networkAddress = new IPv4Address(address.getIpLong() & getMaskAsLong());
        this.networkMetadata = new NetworkMetadata(networkAddress, maskAsLong, maskLength);
    }

    public long maskToLong(int maskLength) {
        validateMask(maskLength);

        return LONG_MAX_IP >> maskLength ^ LONG_MAX_IP;
    }

    public boolean contains(IPv4Address address) {
        return address.greaterThan(getNetworkAddress()) && address.lessThan(getBroadcastAddress());
    }

    public Network[] getSubnets() throws Exceptions.InvalidMaskValueException {
        int newMaskLength = maskLength + 1;
        validateMask(newMaskLength);
        Network[] subnets = new Network[SUBNETS_COUNT];
        long secondSubnetAddress = getFirstUsableAddress().getIpLong() + getTotalHosts() / SUBNETS_COUNT;

        subnets[0] = new Network(new IPv4Address(getFirstUsableAddress().getIpLong()), newMaskLength);
        subnets[1] = new Network(new IPv4Address(secondSubnetAddress), newMaskLength);

        return subnets;
    }

    @Override
    public String toString() {
        return networkMetadata.networkMetadataAsString;
    }

    public String getNetworkAsString() {
        return networkMetadata.netAsString;
    }

    public IPv4Address getNetworkAddress() {
        return networkAddress;
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
}
