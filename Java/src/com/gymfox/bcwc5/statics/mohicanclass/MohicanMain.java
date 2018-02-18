package com.gymfox.bcwc5.statics.mohicanclass;

public class MohicanMain {
    public static void main(String[] args) throws NoLastMohicanException {
        Mohican m1 = new Mohican("m1");
        Mohican m2 = new Mohican("m2");
        Mohican m3 = new Mohican("m3");
        Mohican m4 = new Mohican("m4");
        Mohican m5 = new Mohican("m5");
        Mohican m6 = new Mohican("m6");
        System.out.println("Deleting");
        Mohican.getLastMohican().deleteLastMohican();
        Mohican.getLastMohican().deleteLastMohican();
        Mohican.getLastMohican().deleteLastMohican();
        Mohican.getLastMohican().deleteLastMohican();
        Mohican.getLastMohican().deleteLastMohican();
    }
}
