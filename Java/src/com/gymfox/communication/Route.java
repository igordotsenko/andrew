package com.gymfox.communication;

import java.util.Optional;

public class Route {
    private final Network network;
    private final String interfaceName;
    private final int metric;
    private IPv4Address gateway;
    private String routeAsString;

    public Route(Network network, String interfaceName, int metric) throws Exceptions.InvalidGatewayException {
        this.network = network;
        this.interfaceName = interfaceName;
        this.metric = metric;
    }

    public Route(Network network, String interfaceName, int metric, IPv4Address gateway) throws Exceptions.InvalidGatewayException {
        this(network, interfaceName, metric);
        validate(network.getNetworkAddress(), gateway);
        this.gateway = gateway;
    }

    private String routeToString() {

        return String.format("net: %s, %sinterface: %s, metric: %s", getNetworkAddress()
                        .getNetworkAsString(), getGateway().map(gateway -> "gateway: " + gateway.getIpString() + ", ")
                        .orElse(""),
                getInterfaceName(), getMetric());
    }

    private void validate(IPv4Address netAddress, IPv4Address gateway) throws Exceptions.InvalidGatewayException {
        if ( netAddress.equals(gateway) ) {
            throw new Exceptions.InvalidGatewayException("Net address and gateway are equals");
        }
    }

    @Override
    public String toString() {
        return routeAsString == null ? routeAsString = routeToString() : routeAsString;
    }

    public Optional<IPv4Address> getGateway() {
        return Optional.ofNullable(gateway);
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public int getMetric() {
        return  metric;
    }

    public Network getNetworkAddress() {
        return network;
    }
}
