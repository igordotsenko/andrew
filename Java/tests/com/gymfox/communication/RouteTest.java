package com.gymfox.communication;

import org.junit.Test;
import org.junit.Assert;

public class RouteTest {
    @Test
    public void creationRouteTest() throws Route.InvalidGatewayException {
        Route route1 = new Route(new Network(new IPv4Address("192.168.0.0")),"en10",10, new IPv4Address("192.168.0.1"));
        Route route2 = new Route(new Network(new IPv4Address("135.56.23.19")),  "en10", 10);
        Route route3 = new Route(new Network(new IPv4Address("10.123.0.0"), 20), "en1", 10);
        Route route4 = new Route(new Network(new IPv4Address("0.0.0.0"), 0),"en0", 10, new IPv4Address("192.168.0.1"));
        Route route5 = new Route(new Network(new IPv4Address("10.0.0.0"), 8), "en1", 10, new IPv4Address("10.123.0.1"));

        Assert.assertEquals("net: 192.168.0.0/24, gateway: 192.168.0.1, interface: en10, metric: 10",
                route1.toString());
        Assert.assertEquals("net: 135.56.23.0/24, interface: en10, metric: 10", route2.toString());
        Assert.assertEquals("net: 10.123.0.0/20, interface: en1, metric: 10", route3.toString());
        Assert.assertEquals("net: 0.0.0.0/0, gateway: 192.168.0.1, interface: en0, metric: 10",
                route4.toString());
        Assert.assertEquals("net: 10.0.0.0/8, gateway: 10.123.0.1, interface: en1, metric: 10",
                route5.toString());
    }

    @Test ( expected = Route.InvalidGatewayException.class )
    public void invalidGatewayExceptionTest() throws Route.InvalidGatewayException {
        Route route1 = new Route(new Network(new IPv4Address("192.168.0.0")), "en10", 10, new IPv4Address
                ("192.168.0.0"));
    }
}
