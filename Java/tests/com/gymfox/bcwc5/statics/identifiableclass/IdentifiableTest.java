package com.gymfox.bcwc5.statics.identifiableclass;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class IdentifiableTest {
    @Test
    public void idTest() throws Throwable {
        Identifiable obj1 = new Identifiable();

        assertEquals(Identifiable.getTotalIdCount(), 1);
        assertEquals(obj1.getId(), 1);

        Identifiable obj2 = new Identifiable();
        assertEquals(Identifiable.getTotalIdCount(), 2);
        assertEquals(obj2.getId(), 2);
        Identifiable.reset();
    }

    @Test(expected = NoIdentifiableObjectException.class)
    public void noIdentifiableObject() throws Throwable {
       Identifiable obj3 = new Identifiable();
       System.out.println(Identifiable.getTotalIdCount());

       obj3.delete();
       obj3.delete();
    }
}