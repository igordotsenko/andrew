package com.gymfox.army.units;

import com.gymfox.army.ability.WerewolfAbility;

public class Werewolf extends Unit {
    public Werewolf(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new WerewolfAbility(this);
    }

}
