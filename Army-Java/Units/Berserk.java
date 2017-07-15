package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.BerserkAbility;
import com.gymfox.Army.State.HumanState;

public class Berserk extends Unit {
    public Berserk(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        setAbility(new BerserkAbility(this));
        setUnitType(UnitType.BERSERK);
        setCurrentState(new HumanState(this));
        setNextState(new HumanState(this));
    }
}
