package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.DefaultAbility;
public class Soldier extends Unit {
    public Soldier(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new DefaultAbility(this);
    }
}
