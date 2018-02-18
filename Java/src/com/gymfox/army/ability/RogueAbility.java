package com.gymfox.army.ability;

import com.gymfox.army.units.Unit;

public class RogueAbility extends Ability {
    public RogueAbility(Unit currentUnit) {
        super(currentUnit);
    }

    @Override
    public void attack(Unit victim) throws Unit.UnitIsDeadException {
        victim.takeDamage(getCurrentUnit().getDamage());
    }
}
