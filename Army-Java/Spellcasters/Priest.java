package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.HealerAbility;
import com.gymfox.Army.Ability.PriestAbility;
import com.gymfox.Army.State.HumanState;

public class Priest extends Spellcaster {
    public Priest(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        setUnitType(UnitType.PRIEST);
        setAbility(new PriestAbility(this));
        setCurrentState(new HumanState(this));
        setNextState(new HumanState(this));
        setCurrentSpell("Heal");
    }
}
