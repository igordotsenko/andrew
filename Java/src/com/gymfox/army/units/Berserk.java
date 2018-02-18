package com.gymfox.army.units;

import com.gymfox.army.ability.BerserkAbility;

public class Berserk extends Unit {
    public Berserk(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new BerserkAbility(this);
        this.immunityToMagic = true;
    }
}
