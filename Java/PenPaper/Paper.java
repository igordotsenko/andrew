package com.gymfox.penpaper;

public class Paper {
    private StringBuffer content;
    private int maxSymbols;
    private int symbols;
    private final static int defaultSize = 8;

    public Paper(int maxSymbols) {
        content = new StringBuffer();
        this.maxSymbols = maxSymbols;
    }

    public Paper() {
        this(defaultSize);
    }

    public void addContent(String message) throws OutOfSpaceException {
        if ( content.length() == maxSymbols ) {
            throw new OutOfSpaceException();
        }

        int total = content.length() + maxSymbols;

        if ( total > maxSymbols ) {
            content.append(message.substring(0, maxSymbols - content.length()));
            return;
        }
        content.append(message);
    }

    public void show() {
        System.out.println(content);
    }

    public int getMaxSymbols() {
        return maxSymbols;
    }

    public int getSymbols() {
        return symbols;
    }
}
