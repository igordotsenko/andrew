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
        Assert.assertEquals(2131504406L, new IPv4Address("127.12.45.22").getIpLong());
        Assert.assertEquals(3171620819L, new IPv4Address("189.11.23.211").getIpLong());
        Assert.assertEquals(4294967295L, new IPv4Address("255.255.255.255").getIpLong());
    }

    @Test
    public void equalsIpAsLongTest() {
        Assert.assertEquals(2131504406L, new IPv4Address(2131504406L).getIpLong());
        Assert.assertEquals(3171620819L, new IPv4Address(3171620819L).getIpLong());
        Assert.assertEquals(4294967295L, new IPv4Address(4294967295L).getIpLong());
    }

    @Test
    public void equalsStringWithStringTest() {
        Assert.assertEquals((new IPv4Address("127.12.45.22")), new IPv4Address("127.12.45.22"));
        Assert.assertEquals((new IPv4Address("189.11.23.211")), new IPv4Address("189.11.23.211"));
    }

    @Test
    public void equalsStringWithLongTest() {
        Assert.assertEquals((new IPv4Address("127.12.45.22")), new IPv4Address(2131504406L));
        Assert.assertEquals((new IPv4Address("189.11.23.211")), new IPv4Address(3171620819L));
    }

    @Test
    public void equalsLongWithLongTest() {
        Assert.assertEquals((new IPv4Address(2131504406L)), new IPv4Address(2131504406L));
        Assert.assertEquals((new IPv4Address(3171620819L)), new IPv4Address(3171620819L));
    }

    @Test
    public void ipStringGreaterThanTest() {
        Assert.assertTrue(new IPv4Address("189.11.23.211").greaterThan(new IPv4Address("127.12.45.22")));
    }

    @Test
    public void ipLongGreaterThanTest() {
        Assert.assertTrue(new IPv4Address(3171620819L).greaterThan(new IPv4Address(2131504406L)));
    }

    @Test
    public void ipStringLessThanTest() {
        Assert.assertTrue(new IPv4Address("127.12.45.22").lessThan(new IPv4Address("189.11.23.211")));
    }

    @Test
    public void ipLongLessThanTest() {
        Assert.assertTrue(new IPv4Address(2131504406L).lessThan(new IPv4Address(3171620819L)));
    }

    @Test ( expected = IllegalArgumentException.class )
    public void validateLongTest() {
        new IPv4Address(4294967296L);
    }

    @Test ( expected = IllegalArgumentException.class )
    public void validateOctetsCountTest() {
        new IPv4Address("255.255.255.255.255");
    }

    @Test ( expected = IllegalArgumentException.class )
    public void notEnoughOctetsTest() {
        new IPv4Address("255.255");
    }

    @Test ( expected = IllegalArgumentException.class )
    public void validateNumbersInOctetsTest() {
        new IPv4Address("999.999.999.999");
    }
}



