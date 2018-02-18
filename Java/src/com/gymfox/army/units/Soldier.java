package com.gymfox.army.Units;

import com.gymfox.army.Ability.DefaultAbility;

public class Soldier extends Unit {
    public Soldier(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new DefaultAbility(this);
    }
}
