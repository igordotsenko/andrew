package com.gymfox.penpaper;

public class Pen {
    private int inkCapacity;
    private int inkAmount;
    private final static int defaultSize = 8;

    public Pen(int inkCapacity) {
        this.inkCapacity = inkCapacity;
    }

    public Pen() {
        this(defaultSize);
    }

    public void write(Paper paper, String message) throws OutOfInkException, OutOfSpaceException {
        if ( inkAmount == 0 ) {
            throw new OutOfInkException();
        }

        if ( inkAmount < message.length()) {
            paper.addContent(message.substring(0, inkAmount));
            inkAmount = 0;
            return;
        }

        paper.addContent(message);
        inkAmount -= message.length();
    }

    public void refill() {
        inkAmount = inkCapacity;
    }

    public int getInkCapacity() {
        return inkCapacity;
    }

    public int getInkAmount() {
        return inkAmount;
    }
}