package com.gymfox.communication;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

public class RouterTest {
    private static Set<Route> routes;

    @BeforeClass
    public static void setUpRouters() {
        routes = new TreeSet<Route>(new Router.RouteComparator()) {{
            add(new Route(new Network(new IPv4Address("10.0.0.0"), 8),"en1", 10, new IPv4Address("10.123.0.1")));
            add(new Route(new Network(new IPv4Address("192.168.5.0"), 25), "en0", 10, new IPv4Address("10.123.0.0")));
            add(new Route(new Network(new IPv4Address("192.168.5.0"), 24), "en0", 5, new IPv4Address("192.168.2.1")));
            add(new Route(new Network(new IPv4Address("192.168.1.128"), 25),  "en0", 3));
            add(new Route(new Network(new IPv4Address("10.123.0.0"), 20),  "en1", 10));
        }};
    }

    @Test
    public void getRouterTest() throws Exceptions.InvalidGatewayException {
        Router router = new Router(routes);
        Assert.assertEquals("10.0.0.0/8", router.getRouteForAddress(new IPv4Address("10.192.168.172")).getNetworkAddress().getNetworkAsString());
        Assert.assertEquals("0.0.0.0/0", router.getRouteForAddress(new IPv4Address("172.16.25.157")).getNetworkAddress().getNetworkAsString());
        Assert.assertEquals("192.168.5.0/25", router.getRouteForAddress(new IPv4Address("192.168.5.7")).getNetworkAddress().getNetworkAsString());
        Assert.assertEquals("192.168.5.0/24", router.getRouteForAddress(new IPv4Address("192.168.5.220")).getNetworkAddress().getNetworkAsString());
        Assert.assertEquals("0.0.0.0/0", router.getRouteForAddress(new IPv4Address("192.168.1.52")).getNetworkAddress().getNetworkAsString());
        Assert.assertEquals("10.123.0.0/20", router.getRouteForAddress(new IPv4Address("10.123.5.78")).getNetworkAddress().getNetworkAsString());
    }

    @Test
    public void addAndGetRouteTest() throws Exceptions.InvalidGatewayException {
        Set<Route> routes = new TreeSet<>();
        Router router = new Router(routes);

        router.addRoute(new Route(new Network(new IPv4Address("15.148.16.0"), 20), "en1",10));
        router.addRoute(new Route(new Network(new IPv4Address("172.16.0.0"), 8), "en1",10));

        Assert.assertEquals("15.148.16.0/20", router.getRouteForAddress(new IPv4Address("15.148.25.13")).getNetworkAddress().getNetworkAsString());
        Assert.assertEquals("172.0.0.0/8", router.getRouteForAddress(new IPv4Address("172.156.192.13")).getNetworkAddress().getNetworkAsString());
    }

    @Test
    public void removeRouteTest() throws Exceptions.InvalidGatewayException {
        Router router = new Router(routes);
        Route route = new Route(new Network(new IPv4Address("147.25.0.0"), 16), "en2", 10, new IPv4Address("147.25.36.1"));
        router.addRoute(route);

        Assert.assertEquals("147.25.0.0/16", router.getRouteForAddress(new IPv4Address("147.25.36.19")).getNetworkAddress().getNetworkAsString());

        router.removeRoute(route);

        Assert.assertEquals("0.0.0.0/0", router.getRouteForAddress(new IPv4Address("147.25.36.19")).getNetworkAddress().getNetworkAsString());
    }
}
