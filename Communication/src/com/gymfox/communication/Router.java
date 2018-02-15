package com.gymfox.communication;

import java.util.Comparator;
import java.util.TreeSet;

public class Router {
    static class RouteComparator implements Comparator<Route> {
        @Override
        public int compare(Route route, Route otherRoute) {
            int diffMask = otherRoute.getNetworkAddress().getMaskLength() - route.getNetworkAddress().getMaskLength();

            if ( diffMask == 0 ) {
                return route.getMetric() - otherRoute.getMetric();
            }
            return diffMask;
        }
    }

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

    //todo check it
    public Route getRouteForAddress(IPv4Address address) {
        for ( Route route : routes ) {
            if ( route.getNetworkAddress().contains(address) ) {
                return route;
            }
        }
        return null;
    }

    //todo check it
    public TreeSet<Route> getRoutes() {
        return new TreeSet<Route>(routes);
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

}
