package com.gymfox.army.Units;

import com.gymfox.army.Ability.RogueAbility;
public class Rogue extends Unit {
    public Rogue(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new RogueAbility(this);
    }
}
