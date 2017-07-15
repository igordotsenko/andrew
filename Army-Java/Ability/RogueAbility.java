package com.gymfox.Army.Ability;

import com.gymfox.Army.Exception.UnitIsDeadException;
import com.gymfox.Army.Units.Unit;

public class RogueAbility extends Ability {
    public RogueAbility(Unit currentUnit) {
        super(currentUnit);
    }

    @Override
    public void attack(Unit victim) throws UnitIsDeadException {
        victim.takeDamage(getCurrentUnit().getDamage());
    }
}
