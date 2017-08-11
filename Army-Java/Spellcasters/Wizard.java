package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.WizardAbility;
import com.gymfox.Army.MagicSkills.MagicSkills;

public class Wizard extends Spellcaster {
    public Wizard(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        this.ability = new WizardAbility(this);
        this.currentSpell = spellbook.get("Fireball");
        this.magicPower = new MagicSkills(1.0,0.5);
    }
}
