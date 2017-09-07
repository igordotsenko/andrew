package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.DefaultAbility;
import com.gymfox.Army.MagicSkills.MagicSkills;

public class Wizard extends Spellcaster {
    private static double DEFAULT_BATTLE_MAGIC_SKILL = 1.0;
    private static double DEFAULT_HEAL_MAGIC_SKILL = 0.5;

    public Wizard(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits, new MagicSkills(DEFAULT_BATTLE_MAGIC_SKILL,DEFAULT_HEAL_MAGIC_SKILL),
                "Fireball");
        this.ability = new DefaultAbility(this);
    }
}
