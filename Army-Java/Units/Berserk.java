package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.BerserkAbility;

public class Berserk extends Unit {
    public Berserk(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new BerserkAbility(this);
        this.immunityToMagic = true;
    }
}
