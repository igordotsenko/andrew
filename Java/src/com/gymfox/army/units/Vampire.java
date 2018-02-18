package com.gymfox.army.units;

import com.gymfox.army.ability.VampireAbility;

public class Vampire extends Unit {
    public Vampire(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new VampireAbility(this);
        this.isDead = true;
    }
}
