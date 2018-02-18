package com.gymfox.army.Units;

import com.gymfox.army.Ability.VampireAbility;

public class Vampire extends Unit {
    public Vampire(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new VampireAbility(this);
        this.isDead = true;
    }
}
