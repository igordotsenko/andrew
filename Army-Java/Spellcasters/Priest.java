package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.PriestAbility;
import com.gymfox.Army.MagicSkills.MagicSkills;

public class Priest extends Spellcaster {
    public Priest(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        this.ability = new PriestAbility(this);
        this.currentSpell = spellbook.get("Heal");
        this.magicPower = new MagicSkills(0.5,2);
    }
}
