package com.gymfox.communication;

import org.junit.Test;
import org.testng.Assert;

import java.util.Set;
import java.util.TreeSet;

public class RouterTest {

    @Test
    public void routerTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
        Set<Route> routes = new TreeSet<Route>(new CompareRoute()) {{
            add(new Route(new Network(new IPv4Address("10.0.0.0"), 8), "10.123.0.1", "en1", 10));
            add(new Route(new Network(new IPv4Address("192.168.5.0"), 25), "10.123.0.0", "en0", 10));
            add(new Route(new Network(new IPv4Address("192.168.5.0"), 24), "192.168.2.1", "en0", 5));
            add(new Route(new Network(new IPv4Address("192.168.1.128"), 25), null, "en0", 3));
            add(new Route(new Network(new IPv4Address("10.123.0.0"), 20), null, "en1", 10));
        }};

        Router router = new Router(routes);

        Assert.assertEquals("10.0.0.0/8", router.getRouteForAddress(new IPv4Address("10.192.168.172")).getNetwork().toString());
        Assert.assertEquals("0.0.0.0/0", router.getRouteForAddress(new IPv4Address("172.16.25.157")).getNetwork().toString());
        Assert.assertEquals("192.168.5.0/25", router.getRouteForAddress(new IPv4Address("192.168.5.7")).getNetwork().toString());
        Assert.assertEquals("192.168.5.0/24", router.getRouteForAddress(new IPv4Address("192.168.5.220")).getNetwork().toString());
        Assert.assertEquals("0.0.0.0/0", router.getRouteForAddress(new IPv4Address("192.168.1.52")).getNetwork().toString());
        Assert.assertEquals("10.123.0.0/20", router.getRouteForAddress(new IPv4Address("10.123.5.78")).getNetwork().toString());
    }

    @Test
    public void addAndGetRouteTest() throws Network.InvalidMaskValueExcetion,
            IPv4Address.InvalidOctetsCountException, IPv4Address.InvalidValueInOctetsException {
        Set<Route> routes = new TreeSet<Route>();
        Router router = new Router(routes);

        router.addRoute(new Route(new Network(new IPv4Address("15.148.16.0"), 20), null ,"en1",
                10));

        Assert.assertEquals("15.148.16.0/20", router.getRouteForAddress(new IPv4Address("15.148.25.13")).getNetwork().toString());
    }

    @Test
    public void removeRouteTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
        Set<Route> routes = new TreeSet<Route>(new CompareRoute()) {{
            add(new Route(new Network(new IPv4Address("10.0.0.0"), 8), "10.123.0.1", "en1", 10));
            add(new Route(new Network(new IPv4Address("192.168.5.0"), 25), "10.123.0.0", "en0", 10));
            add(new Route(new Network(new IPv4Address("192.168.5.0"), 24), "192.168.2.1", "en0", 5));
            add(new Route(new Network(new IPv4Address("192.168.1.128"), 25), null, "en0", 3));
            add(new Route(new Network(new IPv4Address("10.123.0.0"), 20), null, "en1", 10));
        }};
        Route route = new Route(new Network(new IPv4Address("147.25.0.0"), 16), "147.25.36.1","en2", 10);
        Router router = new Router(routes);
        router.addRoute(route);

        Assert.assertEquals("147.25.0.0/16", router.getRouteForAddress(new IPv4Address("147.25.36.19")).getNetwork().toString());

        router.removeRoute(route);

        Assert.assertEquals("0.0.0.0/0", router.getRouteForAddress(new IPv4Address("147.25.36.19")).getNetwork().toString());
    }
}
