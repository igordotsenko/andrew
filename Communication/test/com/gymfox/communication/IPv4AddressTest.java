package com.gymfox.communication;

import org.junit.Assert;
import org.junit.Test;

public class IPv4AddressTest {
    @Test
    public void equalsIpAsStringTest() {
        Assert.assertEquals("255.255.255.255", new IPv4Address("255.255.255.255").getIpString());
        Assert.assertEquals("127.12.45.22", new IPv4Address("127.12.45.22").getIpString());
        Assert.assertEquals("189.11.23.211", new IPv4Address("189.11.23.211").getIpString());
    }

    @Test
    public void equalsIpAsLongWithStringTest() {
        Assert.assertEquals(2131504406l, new IPv4Address("127.12.45.22").getIpLong());
        Assert.assertEquals(3171620819l, new IPv4Address("189.11.23.211").getIpLong());
        Assert.assertEquals(4294967295l, new IPv4Address("255.255.255.255").getIpLong());
    }

    @Test
    public void equalsIpAsLongTest() {
        long expectedLongValue1 = 2131504406l;
        long expectedLongValue2 = 3171620819l;
        long expectedLongValue3 = 4294967295l;

        Assert.assertEquals(expectedLongValue1, new IPv4Address(expectedLongValue1).getIpLong());
        Assert.assertEquals(expectedLongValue2, new IPv4Address(expectedLongValue2).getIpLong());
        Assert.assertEquals(expectedLongValue3, new IPv4Address(expectedLongValue3).getIpLong());
    }

    @Test
    public void equalsStringWithStringTest() {
        Assert.assertTrue((new IPv4Address("127.12.45.22")).equals(new IPv4Address("127.12.45.22")));
        Assert.assertTrue((new IPv4Address("189.11.23.211")).equals(new IPv4Address("189.11.23.211")));
    }

    @Test
    public void equalsStringWithLongTest() {
        Assert.assertTrue((new IPv4Address("127.12.45.22")).equals(new IPv4Address(2131504406l)));
        Assert.assertTrue((new IPv4Address("189.11.23.211")).equals(new IPv4Address(3171620819l)));
    }

    @Test
    public void equalsLongWithLongTest() {
        Assert.assertTrue((new IPv4Address(2131504406l)).equals(new IPv4Address(2131504406l)));
        Assert.assertTrue((new IPv4Address(3171620819l)).equals(new IPv4Address(3171620819l)));
    }

    @Test
    public void ipStringGreaterThanTest() {
        IPv4Address ip1 = new IPv4Address("189.11.23.211");
        IPv4Address ip2 = new IPv4Address("127.12.45.22");

        Assert.assertTrue(ip1.greaterThan(ip2));
    }

    @Test
    public void ipLongGreaterThanTest() {
        IPv4Address ip1 = new IPv4Address(3171620819l);
        IPv4Address ip2 = new IPv4Address(2131504406l);

        Assert.assertEquals(true, ip1.greaterThan(ip2));
    }

    @Test
    public void ipStringLessThanTest() {
        IPv4Address ip1 = new IPv4Address("127.12.45.22");
        IPv4Address ip2 = new IPv4Address("189.11.23.211");

        Assert.assertTrue(ip1.lessThan(ip2));
    }

    @Test
    public void ipLongLessThanTest() {
        IPv4Address ip1 = new IPv4Address(2131504406l);
        IPv4Address ip2 = new IPv4Address(3171620819l);

        Assert.assertTrue(ip1.lessThan(ip2));
    }

    @Test ( expected = IllegalArgumentException.class )
    public void validateLongTest() {
        IPv4Address ip = new IPv4Address(4294967296l);
    }

    @Test ( expected = IllegalArgumentException.class )
    public void validateOctetsCountTest() {
        IPv4Address ip = new IPv4Address("255.255.255.255.255");
    }

    @Test ( expected = IllegalArgumentException.class )
    public void notEnoughOctetsTest() {
        IPv4Address ip = new IPv4Address("255.255");
    }

    @Test ( expected = IllegalArgumentException.class )
    public void validateNumbersInOctetsTest() {
        IPv4Address ip = new IPv4Address("999.999.999.999");
    }
}



