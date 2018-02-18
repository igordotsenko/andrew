package com.gymfox.statics.identifiableclass;

public class Identifiable {
    private static int totalIdCount = 0;
    private int id;

    public Identifiable() {
        totalIdCount += 1;
        id = totalIdCount;
    }

    public void delete() throws NoIdentifiableObjectException {
        if ( totalIdCount == 0 ) {
            throw new NoIdentifiableObjectException();
        }

        totalIdCount -= 1;
    }

    static void reset() {
        totalIdCount = 0;
    }

    public static int getTotalIdCount() {
        return totalIdCount;
    }

    public int getId() {
        return id;
    }
}
