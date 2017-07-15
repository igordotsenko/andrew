package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.WerewolfAbility;
import com.gymfox.Army.State.HumanState;
import com.gymfox.Army.State.WolfState;

public class Werewolf extends Unit {
    public Werewolf(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        setAbility(new WerewolfAbility(this));
        setUnitType(UnitType.WEREWOLF);
        setCurrentState(new HumanState(this));
        setNextState(new WolfState(this));
    }

}
