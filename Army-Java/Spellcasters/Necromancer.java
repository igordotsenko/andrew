package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.NecroAbility;
import com.gymfox.Army.Ability.WizardAbility;
import com.gymfox.Army.State.HumanState;

public class Necromancer extends Spellcaster {
    public Necromancer(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        setUnitType(UnitType.NECROMANCER);
        setAbility(new NecroAbility(this));
        setCurrentState(new HumanState(this));
        setNextState(new HumanState(this));
        setCurrentSpell("Fireball");
        setDead();
    }
}
