package com.gymfox.communication;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Router {
    private Set<Route> routes = new TreeSet<Route>(new CompareRoute()){{
        add(new Route(new Network(new IPv4Address("0.0.0.0"), 0),"0.0.0.1", "en0", 10));
    }};

    public Router(Iterable<Route> routes) throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
        for ( Route route : routes ) {
            this.routes.add(route);
        }
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public Route getRouteForAddress(IPv4Address address) {
        for ( Route route : routes ) {
            if ( route.getNetwork().contains(address) ) {
                return route;
            }
        }

        return null;
    }

    public Iterable<Route> getRoutes() {
        return routes;
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }
}

class CompareRoute implements Comparator<Route> {
    @Override
    public int compare(Route route, Route otherRoute) {
        int diffMask = otherRoute.getNetwork().getMaskLength() - route.getNetwork().getMaskLength();

        if ( diffMask == 0 ) {
            int diffMetric = route.getMetric() - otherRoute.getMetric();

            if ( diffMetric == 0 ) {
                return 0;
            }
            return diffMetric;
        }
        return diffMask;
    }
}
