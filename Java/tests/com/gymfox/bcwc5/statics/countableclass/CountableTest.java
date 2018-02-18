package com.gymfox.bcwc5.statics.countableclass;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CountableTest {
    @Test
    public void countTest() throws Throwable {
        Countable obj1 = new Countable();
        Countable obj2 = new Countable();
        Countable obj3 = new Countable();

        assertEquals(Countable.getTotalCount(), 3);
        obj3.destroy();
        assertEquals(Countable.getTotalCount(), 2);
        Countable.reset();
    }

    @Test(expected = NoCountableObjectException.class)
    public void noCountableObject() throws Throwable {
        Countable obj4 = new Countable();
        Countable obj5 = new Countable();
        Countable obj6 = new Countable();

        assertEquals(Countable.getTotalCount(), 3);
        Countable.reset();
        obj6.destroy();
    }
}