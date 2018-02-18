package com.gymfox.bcwc5.unit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTest {
    Unit barbarian = new Unit("Arnold", 120, 30);
    Unit knight = new Unit("Arthur", 100, 10);

    @Test
    public void attack() throws UnitIsDeadException {
        knight.attack(barbarian);
        assertEquals(barbarian.getHitPoints(), 110);
        assertEquals(knight.getHitPoints(), 85);
    }

    @Test
    public void addHitPoints1() throws UnitIsDeadException {
        knight.attack(barbarian);

        barbarian.addHitPoints(50);
        knight.addHitPoints(10);

        assertEquals(barbarian.getHitPoints(), 120);
        assertEquals(knight.getHitPoints(), 95);
    }

    @Test
    public void addHitPoints2() throws UnitIsDeadException {
        knight.attack(barbarian);

        barbarian.addHitPoints(5);
        knight.addHitPoints(5);

        assertEquals(barbarian.getHitPoints(), 115);
        assertEquals(knight.getHitPoints(), 90);
    }

}