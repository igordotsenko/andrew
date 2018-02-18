package com.gymfox.bcwc5.statics.mohicanclass;

import junit.framework.Assert;
import org.junit.Test;

public class MohicanTest {
    @Test (expected = NoLastMohicanException.class)
    public void getNoLastMohican() throws NoLastMohicanException {
        Mohican.getLastMohican();
    }

    @Test
    public void deleteLastMohican() throws NoLastMohicanException {
        Mohican mohican1 = new Mohican("M1");
        Mohican mohican2 = new Mohican("M2");
        Mohican mohican3 = new Mohican("M3");
        Mohican mohican4 = new Mohican("M4");
        Mohican mohican5 = new Mohican("M5");

        System.out.println("Last Mohican is: " + Mohican.getLastMohican() + "; prev is: " + Mohican.getLastMohican()
                .getPrev());
        System.out.println("\t\t\tDELETING");
        Mohican.getLastMohican().deleteLastMohican();
        System.out.println("Last Mohican is: " + Mohican.getLastMohican()
                + "; prev is: " + Mohican.getLastMohican().getPrev());

        Mohican.getLastMohican().deleteLastMohican();
        System.out.println("Last Mohican is: " + Mohican.getLastMohican()
                + "; prev is: " + Mohican.getLastMohican().getPrev());

        Mohican.getLastMohican().deleteLastMohican();
        System.out.println("Last Mohican is: " + Mohican.getLastMohican()
                + "; prev is: " + Mohican.getLastMohican().getPrev());

        Mohican.getLastMohican().deleteLastMohican();
        System.out.println("Last Mohican is: " + Mohican.getLastMohican()
                + "; prev is: " + Mohican.getLastMohican().getPrev());

    }

    @Test
    public void getLastMohican() throws NoLastMohicanException {
        Mohican mohican1 = new Mohican("M1");
        Mohican mohican2 = new Mohican("M2");
        Mohican last = Mohican.getLastMohican();

        Assert.assertEquals(Mohican.getLastMohican(), last);
        Mohican.reset();
    }
}
