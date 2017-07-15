package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.WizardAbility;
import com.gymfox.Army.State.HumanState;

public class Wizard extends Spellcaster {
    public Wizard(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        setUnitType(UnitType.WIZARD);
        setAbility(new WizardAbility(this));
        setCurrentState(new HumanState(this));
        setNextState(new HumanState(this));
        setCurrentSpell("Fireball");
    }
}
