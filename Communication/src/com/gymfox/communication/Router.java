package com.gymfox.communication;

import java.util.Comparator;
import java.util.TreeSet;

public class Router {
    private TreeSet<Route> routes = new TreeSet<Route>(new RouteComparator());

    public Router(TreeSet<Route> newRoutes) throws Route.InvalidGatewayException {
        defaultRoute();

        this.routes.addAll(newRoutes);
    }

    private void defaultRoute() throws  Route.InvalidGatewayException {
        addRoute(new Route(new Network(new IPv4Address("0.0.0.0"), 0), "en0", 10, new IPv4Address("0.0.0.1")));
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public Route getRouteForAddress(IPv4Address address) {
        for ( Route route : routes ) {
            if ( route.getNetworkAddress().contains(address) ) {
                return route;
            }
        }

        throw new RuntimeException();
    }

    public TreeSet<Route> getRoutes() {
        return (TreeSet<Route>)routes.clone();
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }
}

class RouteComparator implements Comparator<Route> {
    @Override
    public int compare(Route route, Route otherRoute) {
        int diffMask = otherRoute.getNetworkAddress().getMaskLength() - route.getNetworkAddress().getMaskLength();

        if ( diffMask == 0 ) {
            return route.getMetric() - otherRoute.getMetric();
        }
        return diffMask;
    }
}
