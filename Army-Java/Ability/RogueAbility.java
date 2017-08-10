package com.gymfox.Army.Ability;

import com.gymfox.Army.Units.Unit;

public class RogueAbility extends Ability {
    public RogueAbility(Unit currentUnit) {
        super(currentUnit);
    }

    @Override
    public void attack(Unit victim) throws Unit.UnitIsDeadException {
        victim.takeDamage(getCurrentUnit().getDamage());
    }
}
