package com.gymfox.communication;

public class Route {
    private Network network;
    private IPv4Address gateway;
    private String interfaceName;
    private int metric;

    public static class InvalidGatewayException extends Exception{}

    public Route(Network network, IPv4Address gateway, String interfaceName, int metric) throws InvalidGatewayException {
        validate(network.getAddress(), gateway);
        this.network = network;
        this.gateway = gateway;
        this.interfaceName = interfaceName;
        this.metric = metric;
    }

    public Route(Network network, Object gateway, String interfaceName, int metric)
            throws IPv4Address.InvalidOctetsCountException, IPv4Address.InvalidValueInOctetsException,
            InvalidGatewayException {
        validate(network.getAddress(), new IPv4Address((String) gateway));
        this.network = network;
        this.gateway = new IPv4Address((String) gateway);
        this.interfaceName = interfaceName;
        this.metric = metric;
    }

    private void validate(IPv4Address netAddres, IPv4Address gateway) throws InvalidGatewayException {
        if ( gateway == null ) {
            return;
        }

        if ( netAddres.equals(gateway) ) {
            throw new InvalidGatewayException();
        }
    }

    public IPv4Address getGateway() {
        return gateway;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public int getMetric() {
        return  metric;
    }

    public Network getNetwork() {
        return network;
    }

    public String toString() {
        StringBuffer out = new StringBuffer();

        out.append("net: " + getNetwork().toString() + ", ");

        if ( getGateway() != null ) {
            out.append("gateway: " + getGateway().toString() + ", ");
        }

        out.append("interface: "+getInterfaceName() + ", metric: " + getMetric());

        return out.toString();
    }
}
