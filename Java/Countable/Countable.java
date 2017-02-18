package com.gymfox.statics.countableclass;

public class Countable {
    private static int totalCount = 0;

    public Countable() {
        totalCount += 1;
    }

    public void destroy() throws NoCountableObjectException{
        if ( totalCount == 0 ) {
            throw new NoCountableObjectException();
        }

        totalCount -= 1;
    }

    static void reset() {
        totalCount = 0;
    }

    public static int getTotalCount() {
        return totalCount;
    }
}
