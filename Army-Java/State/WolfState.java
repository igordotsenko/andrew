package com.gymfox.Army.State;

import com.gymfox.Army.Units.Unit;

public class WolfState extends State {
    public WolfState(Unit currentUnitState) {
        super(currentUnitState);
        setStateName("Wolf");
        setHealthPointLimit(currentUnitState.getHealthPointLimit() * 2);
        setDamage(currentUnitState.getDamage() * 2);
    }

    @Override
    public void takeMagicDamage(int damage) {
        int newDamage = damage * 2;

        tryKill(newDamage);
        currentUnitState.setCurrentHP(currentUnitState.getCurrentHP() - newDamage);
    }
}
