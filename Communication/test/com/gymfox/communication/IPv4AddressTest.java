package com.gymfox.communication;

import com.gymfox.communication.IPv4Address;
import org.junit.Assert;
import org.junit.Test;

public class IPv4AddressTest {
    @Test
    public void equalsIpAsStringTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException {
        Assert.assertEquals("255.255.255.255", new IPv4Address("255.255.255.255").toString());
        Assert.assertEquals("127.12.45.22", new IPv4Address("127.12.45.22").toString());
        Assert.assertEquals("189.11.23.211", new IPv4Address("189.11.23.211").toString());
    }

    @Test
    public void equalsIpAsLongWithStringTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException {
        Assert.assertEquals(2131504406l, new IPv4Address("127.12.45.22").toLong());
        Assert.assertEquals(3171620819l, new IPv4Address("189.11.23.211").toLong());
        Assert.assertEquals(4294967295l, new IPv4Address("255.255.255.255").toLong());
    }

    @Test
    public void equalsIpAsLongTest() {
        Assert.assertEquals(2131504406l, new IPv4Address(2131504406l).toLong());
        Assert.assertEquals(3171620819l, new IPv4Address(3171620819l).toLong());
        Assert.assertEquals(4294967295l, new IPv4Address(4294967295l).toLong());
    }

    @Test
    public void equalsStringWithLongTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException {
        Assert.assertEquals(true, (new IPv4Address("127.12.45.22")).equals(new IPv4Address(2131504406l)));
        Assert.assertEquals(true, (new IPv4Address("189.11.23.211")).equals(new IPv4Address(3171620819l)));
    }

    @Test
    public void ipStringGraterThanTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException {
        IPv4Address ip1 = new IPv4Address("189.11.23.211");
        IPv4Address ip2 = new IPv4Address("127.12.45.22");

        Assert.assertEquals(true, ip1.greaterThan(ip2));
    }

    @Test
    public void ipLongGraterThanTest() {
        IPv4Address ip1 = new IPv4Address(3171620819l);
        IPv4Address ip2 = new IPv4Address(2131504406l);

        Assert.assertEquals(true, ip1.greaterThan(ip2));
    }

    @Test
    public void ipStringLessThanTest() {
        IPv4Address ip1 = new IPv4Address(2131504406l);
        IPv4Address ip2 = new IPv4Address(3171620819l);

        Assert.assertEquals(true, ip1.lessThan(ip2));
    }

    @Test ( expected = IllegalArgumentException.class )
    public void validateLongTest() {
        IPv4Address ip = new IPv4Address(4294967296l);
    }

    @Test ( expected = IPv4Address.InvalidOctetsCountException.class )
    public void validateOctetsCountTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException {
        IPv4Address ip = new IPv4Address("255.255.255.255.255");
    }

    @Test ( expected = IPv4Address.InvalidOctetsCountException.class )
    public void notEnoughOctetsTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException {
        IPv4Address ip = new IPv4Address("255.255");
    }

    @Test ( expected = IPv4Address.InvalidValueInOctetsException.class )
    public void validateNumbersInOctetsTest() throws IPv4Address.InvalidOctetsCountException,
            IPv4Address.InvalidValueInOctetsException {
        IPv4Address ip = new IPv4Address("999.999.999.999");
    }
}



