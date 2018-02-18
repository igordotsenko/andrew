package com.gymfox.bcwc5.statics.passportclass;

import com.gymfox.bcwc5.date.InvalidDateException;
import junit.framework.Assert;
import org.junit.Test;

public class PassportTest {
    @Test
    public void seriesAndNumberSettingTest()
            throws InvalidSerialException, InvalidDateException, InvalidSerialNumberException {
        Passport obj1 = new Passport("ololo", "lolol", 1,1,1970);

        Passport.setSeries("ab");
        Passport obj2 = new Passport("ololo", "lolol", 1,1,1970);

        Passport.setSeries("AC", 100);
        Passport obj3 = new Passport("ololo", "lolol", 1,1,1970);

        Passport.setSeries(999999);
        Passport obj4 = new Passport("ololo", "lolol", 1,1,1970);

        Passport obj5 = new Passport("ololo", "lolol", 1,1,1970);
        Passport obj6 = new Passport("ololo", "lolol", 1,1,1970);
        Passport obj7 = new Passport("ololo", "lolol", 1,1,1970);
        Passport obj8 = new Passport("ololo", "lolol", 1,1,1970);

        Assert.assertEquals("AA000001", obj1.getSeries() + obj1.getSerialNumber());
        Assert.assertEquals("AB000001", obj2.getSeries() + obj2.getSerialNumber());
        Assert.assertEquals("AC000100", obj3.getSeries() + obj3.getSerialNumber());
        Assert.assertEquals("AC999999", obj4.getSeries() + obj4.getSerialNumber());
        Assert.assertEquals("AD000001", obj5.getSeries() + obj5.getSerialNumber());
        Assert.assertEquals("AD000002", obj6.getSeries() + obj6.getSerialNumber());
    }

    @Test(expected = InvalidSerialNumberException.class)
    public void invalidSerialNumberTest()
            throws InvalidSerialException, InvalidDateException, InvalidSerialNumberException {

        Passport.setSeries(10);
        Passport obj9 = new Passport("ololo", "lolol", 1,1,1970);
        Passport.setSeries(9);
        Passport obj10 = new Passport("ololo", "lolol", 1,1,1970);
    }

    @Test(expected = InvalidSerialException.class)
    public void invalidSeriesTest()
            throws InvalidSerialException, InvalidDateException, InvalidSerialNumberException {
        Passport.setSeries("AB");
        Passport obj11 = new Passport("ololo", "lolol",  1,1,1970);
        Passport.setSeries("AA");
        Passport obj12 = new Passport("ololo", "lolol",  1,1,1970);
    }

    @Test(expected = InvalidSerialNumberException.class)
    public void invalidSeriesAndSerialTest()
            throws InvalidSerialException, InvalidDateException, InvalidSerialNumberException {
        Passport.resetSeriesAndNumber();
        Passport.setSeries("AD", 5);
        Passport obj13 = new Passport("ololo", "lolol",  1,1,1970);
        Passport.setSeries("AD", 5);
        Passport obj14 = new Passport("ololo", "lolol",  1,1,1970);
    }

    @Test(expected = InvalidSerialException.class)
    public void lastSeriesAndNumberTest()
            throws InvalidSerialException, InvalidDateException, InvalidSerialNumberException {
        Passport.setSeries("ZZ", 999999);
        Passport obj15 = new Passport("ololo", "lolol",  1,1,1970);
        Passport obj16 = new Passport("ololo", "lolol",  1,1,1970);

    }

}