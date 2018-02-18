package com.gymfox.army.state;

import com.gymfox.army.units.Unit;

public class WolfState extends State {
    public WolfState(Unit currentUnitState) {
        super(currentUnitState);
        setStateName("Wolf");
        setHealthPointLimit(currentUnitState.getHealthPointLimit() * 2);
        setDamage(currentUnitState.getDamage() * 2);
    }

    @Override
    public void takeMagicDamage(int damage) throws Unit.UnitIsDeadException {
        int newDamage = damage * 2;

        tryKill(newDamage);
    }
}
