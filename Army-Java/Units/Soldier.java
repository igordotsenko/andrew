package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.SoldierAbility;
import com.gymfox.Army.State.HumanState;

public class Soldier extends Unit {
    public Soldier(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        setAbility(new SoldierAbility(this));
        setUnitType(UnitType.SOLDIER);
        setCurrentState(new HumanState(this));
        setNextState(new HumanState(this));
    }
}
