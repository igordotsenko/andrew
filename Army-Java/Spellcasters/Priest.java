package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.PriestAbility;

public class Priest extends Spellcaster {
    public Priest(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        this.ability = new PriestAbility(this);

        setCurrentSpell("Heal");
    }
}
