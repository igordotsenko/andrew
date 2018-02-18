package com.gymfox.army.Units;

import com.gymfox.army.Ability.BerserkAbility;

public class Berserk extends Unit {
    public Berserk(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new BerserkAbility(this);
        this.immunityToMagic = true;
    }
}
