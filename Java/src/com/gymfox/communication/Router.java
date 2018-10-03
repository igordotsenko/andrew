package com.gymfox.communication;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Router {
    private Set<Route> routes = new TreeSet<>(new RouteComparator());

    public Router(Set<Route> newRoutes) {
        addDefaultRoute();

        this.routes.addAll(newRoutes);
    }

    private void addDefaultRoute() {
        addRoute(new Route(new Network(new IPv4Address("0.0.0.0"), 0), "en0", 10, new IPv4Address("0.0.0.1")));
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public Route getRouteForAddress(IPv4Address address) {
        return routes.stream().
                filter(route -> route.getNetworkAddress().contains(address)).
                findFirst().
                get();
    }

    public Set<Route> getRoutes() {
        return new TreeSet<>(routes);
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

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
}
