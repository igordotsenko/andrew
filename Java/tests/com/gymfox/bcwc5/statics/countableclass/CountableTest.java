package com.gymfox.bcwc5.statics.countableclass;

import junit.framework.Assert;
import org.junit.Test;

public class CountableTest {
    @Test
    public void countTest() throws Throwable {
        Countable obj1 = new Countable();
        Countable obj2 = new Countable();
        Countable obj3 = new Countable();

        Assert.assertEquals(Countable.getTotalCount(), 3);
        obj3.destroy();
        Assert.assertEquals(Countable.getTotalCount(), 2);
        Countable.reset();
    }

    @Test(expected = NoCountableObjectException.class)
    public void noCountableObject() throws Throwable {
        Countable obj4 = new Countable();
        Countable obj5 = new Countable();
        Countable obj6 = new Countable();

        Assert.assertEquals(Countable.getTotalCount(), 3);
        Countable.reset();
        obj6.destroy();
    }
}