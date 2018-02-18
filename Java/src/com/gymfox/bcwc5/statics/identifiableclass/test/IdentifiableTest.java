package com.gymfox.statics.identifiableclass;

import junit.framework.Assert;
import org.junit.Test;

public class IdentifiableTest {
    @Test
    public void idTest() throws Throwable {
        Identifiable obj1 = new Identifiable();

        Assert.assertEquals(Identifiable.getTotalIdCount(), 1);
        Assert.assertEquals(obj1.getId(), 1);

        Identifiable obj2 = new Identifiable();
        Assert.assertEquals(Identifiable.getTotalIdCount(), 2);
        Assert.assertEquals(obj2.getId(), 2);
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