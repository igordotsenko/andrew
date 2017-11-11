package com.gymfox.communication;

import org.junit.Test;
import org.testng.Assert;

public class NetworkTest {
    @Test ( expected = Network.InvalidMaskValueExcetion.class )
    public void validateGraterMaskTest() throws Network.InvalidMaskValueExcetion,
            IPv4Address.InvalidOctetsCountException, IPv4Address.InvalidValueInOctetsException {
        Network network = new Network(new IPv4Address("192.168.0.0"), 33);
    }

    @Test ( expected = Network.InvalidMaskValueExcetion.class )
    public void validateLessMaskTest() throws Network.InvalidMaskValueExcetion,
            IPv4Address.InvalidOctetsCountException, IPv4Address.InvalidValueInOctetsException {
        Network network = new Network(new IPv4Address("192.168.0.0"), -16);
    }

    @Test
    public void getAddressTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
        Network net1 = new Network(new IPv4Address("192.168.0.1"), 24);
        Network net2 = new Network(new IPv4Address("192.168.0.253"), 0);
        Network net3 = new Network(new IPv4Address("127.12.45.22"), 25);
        Network net4 = new Network(new IPv4Address("189.163.15.0"));

        Assert.assertEquals("192.168.0.0", net1.getAddress().toString());
        Assert.assertEquals("0.0.0.0", net2.getAddress().toString());
        Assert.assertEquals("127.12.45.0", net3.getAddress().toString());
        Assert.assertEquals("189.163.15.0", net4.getAddress().toString());
    }

    @Test
    public void getMaskStringTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
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

        Assert.assertEquals("255.255.224.0", net1.getMaskString());
        Assert.assertEquals("255.255.240.0", net2.getMaskString());
        Assert.assertEquals("255.255.248.0", net3.getMaskString());
        Assert.assertEquals("255.255.252.0", net4.getMaskString());
        Assert.assertEquals("255.255.254.0", net5.getMaskString());
        Assert.assertEquals("255.255.255.0", net6.getMaskString());
        Assert.assertEquals("255.255.255.128", net7.getMaskString());
        Assert.assertEquals("255.255.255.192", net8.getMaskString());
        Assert.assertEquals("255.255.255.224", net9.getMaskString());
        Assert.assertEquals("255.255.255.240", net10.getMaskString());
        Assert.assertEquals("255.255.255.248", net11.getMaskString());
        Assert.assertEquals("255.255.255.252", net12.getMaskString());
    }

    @Test
    public void getBroadcastAddressTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
        Network net1 = new Network(new IPv4Address("192.168.0.13"), 24);
        Network net2 = new Network(new IPv4Address("192.168.0.13"), 25);
        Network net3 = new Network(new IPv4Address("192.168.0.153"), 25);
        Network net4 = new Network(new IPv4Address("127.12.45.22"), 25);
        Network net5 = new Network(new IPv4Address("192.168.0.0"), 32);

        Assert.assertEquals("192.168.0.255", net1.getBroadcastAddress().toString());
        Assert.assertEquals("192.168.0.127", net2.getBroadcastAddress().toString());
        Assert.assertEquals("192.168.0.255", net3.getBroadcastAddress().toString());
        Assert.assertEquals("127.12.45.127", net4.getBroadcastAddress().toString());
        Assert.assertEquals("192.168.0.0", net5.getBroadcastAddress().toString());
    }

    @Test
    public void getFirstUsableAddress() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
        Network net1 = new Network(new IPv4Address("192.168.0.0"), 24);
        Network net2 = new Network(new IPv4Address("192.168.0.13"), 25);
        Network net3 = new Network(new IPv4Address("192.168.0.153"), 25);
        Network net4 = new Network(new IPv4Address("127.12.45.165"), 25);

        Assert.assertEquals("192.168.0.1", net1.getFirstUsableAddress().toString());
        Assert.assertEquals("192.168.0.1", net2.getFirstUsableAddress().toString());
        Assert.assertEquals("192.168.0.129", net3.getFirstUsableAddress().toString());
        Assert.assertEquals("127.12.45.129", net4.getFirstUsableAddress().toString());
    }

    @Test
    public void getLastUsableAddress() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
        Network net1 = new Network(new IPv4Address("192.168.0.0"), 24);
        Network net2 = new Network(new IPv4Address("192.168.0.13"), 25);
        Network net3 = new Network(new IPv4Address("192.168.0.153"), 25);

        Assert.assertEquals("192.168.0.254", net1.getLastUsableAddress().toString());
        Assert.assertEquals("192.168.0.126", net2.getLastUsableAddress().toString());
        Assert.assertEquals("192.168.0.254", net3.getLastUsableAddress().toString());
    }

    @Test
    public void containsTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
        Network net1 = new Network(new IPv4Address("192.168.0.0"), 24);
        Network net2 = new Network(new IPv4Address("0.0.0.0"), 0);

        IPv4Address ip1 = new IPv4Address("192.168.0.1");
        IPv4Address ip2 = new IPv4Address("192.168.1.1");
        IPv4Address ip3 = new IPv4Address("192.167.0.1");
        IPv4Address ip4 = new IPv4Address("167.28.0.1");
        IPv4Address ip5 = new IPv4Address("127.12.45.22");

        Assert.assertEquals(true, net1.contains(ip1));
        Assert.assertEquals(false, net1.contains(ip2));
        Assert.assertEquals(false, net1.contains(ip3));
        Assert.assertEquals(false, net1.contains(ip4));
        Assert.assertEquals(false, net1.contains(ip5));
        Assert.assertEquals(true, net2.contains(ip1));
    }

    @Test
    public void isPublicTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
        Network net1 = new Network(new IPv4Address("10.0.0.0"), 24);
        Network net2 = new Network(new IPv4Address("172.16.0.0"), 24);
        Network net3 = new Network(new IPv4Address("192.168.0.0"), 24);
        Network net4 = new Network(new IPv4Address("189.128.13.25"), 24);
        Network net5 = new Network(new IPv4Address("127.12.45.22"), 25);

        Assert.assertEquals(false, net1.isPublic());
        Assert.assertEquals(false, net2.isPublic());
        Assert.assertEquals(false, net3.isPublic());
        Assert.assertEquals(true, net4.isPublic());
        Assert.assertEquals(true, net5.isPublic());
    }

    @Test
    public void getTotalHostsTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException, Network.InvalidMaskValueExcetion {
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
    public void getSubnetsTest() throws Network.InvalidMaskValueExcetion, Network.InvalidNewMaskLenghtException,
            IPv4Address.InvalidOctetsCountException, IPv4Address.InvalidValueInOctetsException {
        Network net = new Network(new IPv4Address("192.168.0.0"));
        Network[] subnet = net.getSubnets();

        Assert.assertEquals("192.168.0.0/25", subnet[0].toString());
        Assert.assertEquals("192.168.0.0",subnet[0].getAddress().toString());
        Assert.assertEquals("192.168.0.1",subnet[0].getFirstUsableAddress().toString());
        Assert.assertEquals("192.168.0.126",subnet[0].getLastUsableAddress().toString());
        Assert.assertEquals("192.168.0.127",subnet[0].getBroadcastAddress().toString());
        Assert.assertEquals("192.168.0.128/25", subnet[1].toString());
        Assert.assertEquals("192.168.0.128",subnet[1].getAddress().toString());
        Assert.assertEquals("192.168.0.129",subnet[1].getFirstUsableAddress().toString());
        Assert.assertEquals("192.168.0.254",subnet[1].getLastUsableAddress().toString());
        Assert.assertEquals("192.168.0.255",subnet[1].getBroadcastAddress().toString());
    }
}
