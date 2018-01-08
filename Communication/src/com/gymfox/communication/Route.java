package com.gymfox.communication;

public class Route {
    private final Network network;
    private final String interfaceName;
    private final int metric;
    private final String routeAsString;
    private IPv4Address gateway;

    public static class InvalidGatewayException extends IllegalArgumentException{
        public InvalidGatewayException(String errorMessage) {
            super(errorMessage);
        }
    }

    public Route(Network network, String interfaceName, int metric, IPv4Address gateway) throws InvalidGatewayException {
        validate(network.getNetworkAddress(), gateway);
        this.network = network;
        this.interfaceName = interfaceName;
        this.metric = metric;
        this.gateway = gateway;
        this.routeAsString = routeToString();
    }

    public Route(Network network, String interfaceName, int metric) {
        this.network = network;
        this.interfaceName = interfaceName;
        this.metric = metric;
        this.routeAsString = routeToString();
    }

    private String routeToString() {
        StringBuffer out = new StringBuffer();

        out.append("net: " + getNetworkAddress().getNetwork() + ", ");

        if ( gateway != null ) {
            out.append("gateway: " + getGateway().getIpString() + ", ");
        }

        out.append("interface: "+getInterfaceName() + ", metric: " + getMetric());

        return out.toString();
    }

    private void validate(IPv4Address netAddres, IPv4Address gateway) throws InvalidGatewayException {
        if ( netAddres.equals(gateway) ) {
            throw new InvalidGatewayException("Net address and gateway are equals");
        }
    }

    @Override
    public String toString() {
        return routeAsString;
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

    public Network getNetworkAddress() {
        return network;
    }
}
