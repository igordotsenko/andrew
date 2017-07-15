package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.VampireAbility;
import com.gymfox.Army.State.HumanState;

public class Vampire extends Unit {
    public Vampire(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        setAbility(new VampireAbility(this));
        setUnitType(UnitType.VAMPIRE);
        setCurrentState(new HumanState(this));
        setNextState(new HumanState(this));
        setDead();
    }
}
