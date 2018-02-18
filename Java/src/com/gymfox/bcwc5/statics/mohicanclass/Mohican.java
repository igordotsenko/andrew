package com.gymfox.bcwc5.statics.mohicanclass;

public class Mohican {
    private static Mohican lastMohican = null;
    private Mohican prev;
    private String name;

    public Mohican(String name) throws NoLastMohicanException {
        this.name = name;

        prev = lastMohican;
        lastMohican = this;
        System.out.println("Last Mohican was borned: " + getLastMohican() + "; prev is: " + getPrev());
    }

    public void deleteLastMohican() throws NoLastMohicanException {
        validate();

        lastMohican = prev;
        prev = this;
    }

    private static void validate() throws NoLastMohicanException {
        if ( lastMohican == null ) {
            throw new NoLastMohicanException();
        }
    }

    static void reset() {
        lastMohican = null;
    }

    public String toString() {
        return getName();
    }

    public static Mohican getLastMohican() throws NoLastMohicanException {
        validate();

        return lastMohican;
    }

    public Mohican getPrev() {
        return prev;
    }

    public String getName() {
        return name;
    }
}
