package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.WizardAbility;

public class Wizard extends Spellcaster {
    public Wizard(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        this.ability = new WizardAbility(this);
        setCurrentSpell("Fireball");
    }
}
