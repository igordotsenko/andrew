package com.gymfox.communication;

import org.junit.Test;
import org.junit.Assert;

public class NetworkTest {
    @Test ( expected = IllegalArgumentException.class )
    public void validateGreaterMaskTest() {
        new Network(new IPv4Address("192.168.0.0"), 33);
    }

    @Test ( expected = IllegalArgumentException.class )
    public void validateLessMaskTest() {
        new Network(new IPv4Address("192.168.0.0"), -16);
    }

    @Test
    public void getNetworkAsStringTest() {
        Assert.assertEquals("192.168.0.0/19", new Network(new IPv4Address("192.168.0.13"), 19).getNetworkAsString());
        Assert.assertEquals("192.168.0.0/20", new Network(new IPv4Address("192.168.0.13"), 20).getNetworkAsString());
        Assert.assertEquals("192.168.0.0/21", new Network(new IPv4Address("192.168.0.13"), 21).getNetworkAsString());
        Assert.assertEquals("192.168.0.0/22", new Network(new IPv4Address("192.168.0.13"), 22).getNetworkAsString());
        Assert.assertEquals("192.168.0.0/23", new Network(new IPv4Address("192.168.0.13"), 23).getNetworkAsString());
        Assert.assertEquals("192.168.0.0/24", new Network(new IPv4Address("192.168.0.13"), 24).getNetworkAsString());
        Assert.assertEquals("192.168.0.0/25", new Network(new IPv4Address("192.168.0.13"), 25).getNetworkAsString());
        Assert.assertEquals("192.168.0.0/26", new Network(new IPv4Address("192.168.0.13"), 26).getNetworkAsString());
        Assert.assertEquals("192.168.0.0/27", new Network(new IPv4Address("192.168.0.13"), 27).getNetworkAsString());
        Assert.assertEquals("192.168.0.0/28", new Network(new IPv4Address("192.168.0.13"), 28).getNetworkAsString());
        Assert.assertEquals("192.168.0.8/29", new Network(new IPv4Address("192.168.0.13"), 29).getNetworkAsString());
        Assert.assertEquals("192.168.0.12/30", new Network(new IPv4Address("192.168.0.13"), 30).getNetworkAsString());
    }

    @Test
    public void getAddressTest() {
        Assert.assertEquals("192.168.0.0", new Network(new IPv4Address("192.168.0.1"), 24).getNetworkAddress().getIpString());
        Assert.assertEquals("0.0.0.0", new Network(new IPv4Address("192.168.0.253"), 0).getNetworkAddress().getIpString());
        Assert.assertEquals("127.12.45.0", new Network(new IPv4Address("127.12.45.22"), 25).getNetworkAddress().getIpString());
        Assert.assertEquals("189.163.15.0", new Network(new IPv4Address("189.163.15.0")).getNetworkAddress().getIpString());
    }

    @Test
    public void getMaskStringTest() {
        Assert.assertEquals("255.255.224.0", new Network(new IPv4Address("192.168.0.13"), 19).getMaskAsString());
        Assert.assertEquals("255.255.240.0", new Network(new IPv4Address("192.168.0.13"), 20).getMaskAsString());
        Assert.assertEquals("255.255.248.0", new Network(new IPv4Address("192.168.0.13"), 21).getMaskAsString());
        Assert.assertEquals("255.255.252.0", new Network(new IPv4Address("192.168.0.13"), 22).getMaskAsString());
        Assert.assertEquals("255.255.254.0", new Network(new IPv4Address("192.168.0.13"), 23).getMaskAsString());
        Assert.assertEquals("255.255.255.0", new Network(new IPv4Address("192.168.0.13"), 24).getMaskAsString());
        Assert.assertEquals("255.255.255.128", new Network(new IPv4Address("192.168.0.13"), 25).getMaskAsString());
        Assert.assertEquals("255.255.255.192", new Network(new IPv4Address("192.168.0.13"), 26).getMaskAsString());
        Assert.assertEquals("255.255.255.224", new Network(new IPv4Address("192.168.0.13"), 27).getMaskAsString());
        Assert.assertEquals("255.255.255.240", new Network(new IPv4Address("192.168.0.13"), 28).getMaskAsString());
        Assert.assertEquals("255.255.255.248", new Network(new IPv4Address("192.168.0.13"), 29).getMaskAsString());
        Assert.assertEquals("255.255.255.252", new Network(new IPv4Address("192.168.0.13"), 30).getMaskAsString());
    }

    @Test
    public void getMaskAsLongTest() {
        Assert.assertEquals(4294959104L, new Network(new IPv4Address("192.168.0.13"), 19).getMaskAsLong());
        Assert.assertEquals(4294963200L, new Network(new IPv4Address("192.168.0.13"), 20).getMaskAsLong());
        Assert.assertEquals(4294965248L, new Network(new IPv4Address("192.168.0.13"), 21).getMaskAsLong());
        Assert.assertEquals(4294966272L, new Network(new IPv4Address("192.168.0.13"), 22).getMaskAsLong());
        Assert.assertEquals(4294966784L, new Network(new IPv4Address("192.168.0.13"), 23).getMaskAsLong());
        Assert.assertEquals(4294967040L, new Network(new IPv4Address("192.168.0.13"), 24).getMaskAsLong());
        Assert.assertEquals(4294967168L, new Network(new IPv4Address("192.168.0.13"), 25).getMaskAsLong());
        Assert.assertEquals(4294967232L, new Network(new IPv4Address("192.168.0.13"), 26).getMaskAsLong());
        Assert.assertEquals(4294967264L, new Network(new IPv4Address("192.168.0.13"), 27).getMaskAsLong());
        Assert.assertEquals(4294967280L, new Network(new IPv4Address("192.168.0.13"), 28).getMaskAsLong());
        Assert.assertEquals(4294967288L, new Network(new IPv4Address("192.168.0.13"), 29).getMaskAsLong());
        Assert.assertEquals(4294967292L, new Network(new IPv4Address("192.168.0.13"), 30).getMaskAsLong());
    }

    @Test
    public void getBroadcastAddressTest() {
        Assert.assertEquals("192.168.0.255", new Network(new IPv4Address("192.168.0.13"), 24).getBroadcastAddress().getIpString());
        Assert.assertEquals("192.168.0.127", new Network(new IPv4Address("192.168.0.13"), 25).getBroadcastAddress().getIpString());
        Assert.assertEquals("192.168.0.255", new Network(new IPv4Address("192.168.0.153"), 25).getBroadcastAddress().getIpString());
        Assert.assertEquals("127.12.45.127", new Network(new IPv4Address("127.12.45.22"), 25).getBroadcastAddress().getIpString());
        Assert.assertEquals("192.168.0.0", new Network(new IPv4Address("192.168.0.0"), 32).getBroadcastAddress().getIpString());
    }

    @Test
    public void getFirstUsableAddress() {
        Assert.assertEquals("192.168.0.1", new Network(new IPv4Address("192.168.0.0"), 24).getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.1", new Network(new IPv4Address("192.168.0.13"), 25).getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.129", new Network(new IPv4Address("192.168.0.153"), 25).getFirstUsableAddress().getIpString());
        Assert.assertEquals("127.12.45.129", new Network(new IPv4Address("127.12.45.165"), 25).getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.1", new Network(new IPv4Address("192.168.0.1"), 31).getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.2", new Network(new IPv4Address("192.168.0.1"), 32).getFirstUsableAddress().getIpString());
    }

    @Test
    public void getLastUsableAddress() {
        Assert.assertEquals("192.168.0.254", new Network(new IPv4Address("192.168.0.0"), 24).getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.126", new Network(new IPv4Address("192.168.0.13"), 25).getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.254", new Network(new IPv4Address("192.168.0.153"), 25).getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.0", new Network(new IPv4Address("192.168.0.1"), 31).getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.0", new Network(new IPv4Address("192.168.0.1"), 32).getLastUsableAddress().getIpString());
    }

    @Test
    public void containsTest() {
        Network net1 = new Network(new IPv4Address("192.168.0.0"), 24);
        Network net2 = new Network(new IPv4Address("0.0.0.0"), 0);

        IPv4Address ip1 = new IPv4Address("192.168.0.1");
        IPv4Address ip2 = new IPv4Address("192.168.1.1");
        IPv4Address ip3 = new IPv4Address("192.167.0.1");
        IPv4Address ip4 = new IPv4Address("167.28.0.1");
        IPv4Address ip5 = new IPv4Address("127.12.45.22");

        Assert.assertTrue( net1.contains(ip1));
        Assert.assertFalse(net1.contains(ip2));
        Assert.assertFalse(net1.contains(ip3));
        Assert.assertFalse(net1.contains(ip4));
        Assert.assertFalse(net1.contains(ip5));
        Assert.assertTrue(net2.contains(ip1));
        Assert.assertTrue(net2.contains(ip2));
        Assert.assertTrue(net2.contains(ip3));
        Assert.assertTrue(net2.contains(ip4));
        Assert.assertTrue(net2.contains(ip5));
    }

    @Test
    public void isPublicTest() {
        Assert.assertFalse(new Network(new IPv4Address("10.0.0.0"), 24).isPublic());
        Assert.assertFalse(new Network(new IPv4Address("172.16.0.0"), 24).isPublic());
        Assert.assertFalse(new Network(new IPv4Address("192.168.0.0"), 24).isPublic());
        Assert.assertTrue(new Network(new IPv4Address("189.128.13.25"), 24).isPublic());
        Assert.assertTrue(new Network(new IPv4Address("127.12.45.22"), 25).isPublic());
    }

    @Test
    public void getTotalHostsTest() {
        Assert.assertEquals(16777214, new Network(new IPv4Address("192.168.0.0"), 8).getTotalHosts());
        Assert.assertEquals(65534, new Network(new IPv4Address("192.168.0.0"), 16).getTotalHosts());
        Assert.assertEquals(254, new Network(new IPv4Address("192.168.0.0"), 24).getTotalHosts());
        Assert.assertEquals(6, new Network(new IPv4Address("192.168.0.0"), 29).getTotalHosts());
        Assert.assertEquals(2, new Network(new IPv4Address("192.168.0.0"), 30).getTotalHosts());
        Assert.assertEquals(0, new Network(new IPv4Address("192.168.0.0"), 31).getTotalHosts());
    }

    @Test
    public void getSubnetsTest() {
        Network net = new Network(new IPv4Address("192.168.0.0"));
        Network[] subnet = net.getSubnets();

        Assert.assertEquals("192.168.0.0/25", subnet[0].getNetworkAsString());
        Assert.assertEquals("192.168.0.0",subnet[0].getNetworkAddress().getIpString());
        Assert.assertEquals("192.168.0.1",subnet[0].getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.126",subnet[0].getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.127",subnet[0].getBroadcastAddress().getIpString());
        Assert.assertEquals("192.168.0.128/25", subnet[1].getNetworkAsString());
        Assert.assertEquals("192.168.0.128",subnet[1].getNetworkAddress().getIpString());
        Assert.assertEquals("192.168.0.129",subnet[1].getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.254",subnet[1].getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.255",subnet[1].getBroadcastAddress().getIpString());
    }

    @Test ( expected = IllegalArgumentException.class )
    public void invalidSubnetsMaskTest() {
        new Network(new IPv4Address("172.16.0.0"), 32).getSubnets();
    }
}
