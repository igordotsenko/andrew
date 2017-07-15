package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.RogueAbility;
import com.gymfox.Army.State.HumanState;

public class Rogue extends Unit {
    public Rogue(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        setAbility(new RogueAbility(this));
        setUnitType(UnitType.ROGUE);
        setCurrentState(new HumanState(this));
        setNextState(new HumanState(this));
    }
}
