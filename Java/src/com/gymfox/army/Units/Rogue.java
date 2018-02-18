package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.RogueAbility;
public class Rogue extends Unit {
    public Rogue(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new RogueAbility(this);
    }
}
