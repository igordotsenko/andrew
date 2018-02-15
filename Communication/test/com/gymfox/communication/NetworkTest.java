package com.gymfox.communication;

import org.junit.Test;
import org.testng.Assert;

public class NetworkTest {
    @Test ( expected = IllegalArgumentException.class )
    public void validateGraterMaskTest() {
        Network network = new Network(new IPv4Address("192.168.0.0"), 33);
    }

    @Test ( expected = IllegalArgumentException.class )
    public void validateLessMaskTest() {
        Network network = new Network(new IPv4Address("192.168.0.0"), -16);
    }

    @Test
    public void getNetworkTest() {
        Network net1 = new Network(new IPv4Address("192.168.0.13"), 19);
        Network net2 = new Network(new IPv4Address("192.168.0.13"), 20);
        Network net3 = new Network(new IPv4Address("192.168.0.13"), 21);
        Network net4 = new Network(new IPv4Address("192.168.0.13"), 22);
        Network net5 = new Network(new IPv4Address("192.168.0.13"), 23);
        Network net6 = new Network(new IPv4Address("192.168.0.13"), 24);
        Network net7 = new Network(new IPv4Address("192.168.0.13"), 25);
        Network net8 = new Network(new IPv4Address("192.168.0.13"), 26);
        Network net9 = new Network(new IPv4Address("192.168.0.13"), 27);
        Network net10 = new Network(new IPv4Address("192.168.0.13"), 28);
        Network net11 = new Network(new IPv4Address("192.168.0.13"), 29);
        Network net12 = new Network(new IPv4Address("192.168.0.13"), 30);

        Assert.assertEquals("192.168.0.0/19", net1.toString());
        Assert.assertEquals("192.168.0.0/20", net2.toString());
        Assert.assertEquals("192.168.0.0/21", net3.toString());
        Assert.assertEquals("192.168.0.0/22", net4.toString());
        Assert.assertEquals("192.168.0.0/23", net5.toString());
        Assert.assertEquals("192.168.0.0/24", net6.toString());
        Assert.assertEquals("192.168.0.0/25", net7.toString());
        Assert.assertEquals("192.168.0.0/26", net8.toString());
        Assert.assertEquals("192.168.0.0/27", net9.toString());
        Assert.assertEquals("192.168.0.0/28", net10.toString());
        Assert.assertEquals("192.168.0.8/29", net11.toString());
        Assert.assertEquals("192.168.0.12/30", net12.toString());
    }

    @Test
    public void getAddressTest() {
        Network net1 = new Network(new IPv4Address("192.168.0.1"), 24);
        Network net2 = new Network(new IPv4Address("192.168.0.253"), 0);
        Network net3 = new Network(new IPv4Address("127.12.45.22"), 25);
        Network net4 = new Network(new IPv4Address("189.163.15.0"));

        Assert.assertEquals("192.168.0.0", net1.getNetworkAddress().getIpString());
        Assert.assertEquals("0.0.0.0", net2.getNetworkAddress().getIpString());
        Assert.assertEquals("127.12.45.0", net3.getNetworkAddress().getIpString());
        Assert.assertEquals("189.163.15.0", net4.getNetworkAddress().getIpString());
    }

    @Test
    public void getMaskStringTest() {
        Network net1 = new Network(new IPv4Address("192.168.0.13"), 19);
        Network net2 = new Network(new IPv4Address("192.168.0.13"), 20);
        Network net3 = new Network(new IPv4Address("192.168.0.13"), 21);
        Network net4 = new Network(new IPv4Address("192.168.0.13"), 22);
        Network net5 = new Network(new IPv4Address("192.168.0.13"), 23);
        Network net6 = new Network(new IPv4Address("192.168.0.13"), 24);
        Network net7 = new Network(new IPv4Address("192.168.0.13"), 25);
        Network net8 = new Network(new IPv4Address("192.168.0.13"), 26);
        Network net9 = new Network(new IPv4Address("192.168.0.13"), 27);
        Network net10 = new Network(new IPv4Address("192.168.0.13"), 28);
        Network net11 = new Network(new IPv4Address("192.168.0.13"), 29);
        Network net12 = new Network(new IPv4Address("192.168.0.13"), 30);

        Assert.assertEquals("255.255.224.0", net1.getMaskAsString());
        Assert.assertEquals("255.255.240.0", net2.getMaskAsString());
        Assert.assertEquals("255.255.248.0", net3.getMaskAsString());
        Assert.assertEquals("255.255.252.0", net4.getMaskAsString());
        Assert.assertEquals("255.255.254.0", net5.getMaskAsString());
        Assert.assertEquals("255.255.255.0", net6.getMaskAsString());
        Assert.assertEquals("255.255.255.128", net7.getMaskAsString());
        Assert.assertEquals("255.255.255.192", net8.getMaskAsString());
        Assert.assertEquals("255.255.255.224", net9.getMaskAsString());
        Assert.assertEquals("255.255.255.240", net10.getMaskAsString());
        Assert.assertEquals("255.255.255.248", net11.getMaskAsString());
        Assert.assertEquals("255.255.255.252", net12.getMaskAsString());
    }

    @Test
    public void getBroadcastAddressTest() {
        Network net1 = new Network(new IPv4Address("192.168.0.13"), 24);
        Network net2 = new Network(new IPv4Address("192.168.0.13"), 25);
        Network net3 = new Network(new IPv4Address("192.168.0.153"), 25);
        Network net4 = new Network(new IPv4Address("127.12.45.22"), 25);
        Network net5 = new Network(new IPv4Address("192.168.0.0"), 32);

        Assert.assertEquals("192.168.0.255", net1.getBroadcastAddress().getIpString());
        Assert.assertEquals("192.168.0.127", net2.getBroadcastAddress().getIpString());
        Assert.assertEquals("192.168.0.255", net3.getBroadcastAddress().getIpString());
        Assert.assertEquals("127.12.45.127", net4.getBroadcastAddress().getIpString());
        Assert.assertEquals("192.168.0.0", net5.getBroadcastAddress().getIpString());
    }

    @Test
    public void getFirstUsableAddress() {
        Network net1 = new Network(new IPv4Address("192.168.0.0"), 24);
        Network net2 = new Network(new IPv4Address("192.168.0.13"), 25);
        Network net3 = new Network(new IPv4Address("192.168.0.153"), 25);
        Network net4 = new Network(new IPv4Address("127.12.45.165"), 25);

        Assert.assertEquals("192.168.0.1", net1.getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.1", net2.getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.129", net3.getFirstUsableAddress().getIpString());
        Assert.assertEquals("127.12.45.129", net4.getFirstUsableAddress().getIpString());
    }

    @Test
    public void getLastUsableAddress() {
        Network net1 = new Network(new IPv4Address("192.168.0.0"), 24);
        Network net2 = new Network(new IPv4Address("192.168.0.13"), 25);
        Network net3 = new Network(new IPv4Address("192.168.0.153"), 25);

        Assert.assertEquals("192.168.0.254", net1.getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.126", net2.getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.254", net3.getLastUsableAddress().getIpString());
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
    }

    @Test
    public void isPublicTest() {
        Network net1 = new Network(new IPv4Address("10.0.0.0"), 24);
        Network net2 = new Network(new IPv4Address("172.16.0.0"), 24);
        Network net3 = new Network(new IPv4Address("192.168.0.0"), 24);
        Network net4 = new Network(new IPv4Address("189.128.13.25"), 24);
        Network net5 = new Network(new IPv4Address("127.12.45.22"), 25);

        Assert.assertFalse(net1.isPublic());
        Assert.assertFalse(net2.isPublic());
        Assert.assertFalse(net3.isPublic());
        Assert.assertTrue(net4.isPublic());
        Assert.assertTrue(net5.isPublic());
    }

    @Test
    public void getTotalHostsTest() {
        Network net1 = new Network(new IPv4Address("192.168.0.0"), 8);
        Network net2 = new Network(new IPv4Address("192.168.0.0"), 16);
        Network net3 = new Network(new IPv4Address("192.168.0.0"), 24);
        Network net4 = new Network(new IPv4Address("192.168.0.0"), 29);
        Network net5 = new Network(new IPv4Address("192.168.0.0"), 30);
        Network net6 = new Network(new IPv4Address("192.168.0.0"), 31);

        Assert.assertEquals(16777214, net1.getTotalHosts());
        Assert.assertEquals(65534, net2.getTotalHosts());
        Assert.assertEquals(254, net3.getTotalHosts());
        Assert.assertEquals(6, net4.getTotalHosts());
        Assert.assertEquals(2, net5.getTotalHosts());
        Assert.assertEquals(0, net6.getTotalHosts());
    }

    @Test
    public void getSubnetsTest() {
        Network net = new Network(new IPv4Address("192.168.0.0"));
        Network[] subnet = net.getSubnets();

        Assert.assertEquals("192.168.0.0/25", subnet[0].toString());
        Assert.assertEquals("192.168.0.0",subnet[0].getNetworkAddress().getIpString());
        Assert.assertEquals("192.168.0.1",subnet[0].getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.126",subnet[0].getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.127",subnet[0].getBroadcastAddress().getIpString());
        Assert.assertEquals("192.168.0.128/25", subnet[1].toString());
        Assert.assertEquals("192.168.0.128",subnet[1].getNetworkAddress().getIpString());
        Assert.assertEquals("192.168.0.129",subnet[1].getFirstUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.254",subnet[1].getLastUsableAddress().getIpString());
        Assert.assertEquals("192.168.0.255",subnet[1].getBroadcastAddress().getIpString());
    }

    @Test ( expected = IllegalArgumentException.class )
    public void invalidSubnetsMaskTest() {
        Network net = new Network(new IPv4Address("172.16.0.0"), 32);
        Network[] subnet = net.getSubnets();
    }
}
