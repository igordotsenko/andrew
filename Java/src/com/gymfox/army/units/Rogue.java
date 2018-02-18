package com.gymfox.army.units;

import com.gymfox.army.ability.RogueAbility;
public class Rogue extends Unit {
    public Rogue(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new RogueAbility(this);
    }
}
