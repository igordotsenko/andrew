package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.HealerAbility;
import com.gymfox.Army.State.HumanState;

public class Healer extends Spellcaster {
    public Healer(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        this.ability = new HealerAbility(this);
        setCurrentSpell("Heal");
    }
}
