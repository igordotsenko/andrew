package com.gymfox.army.Units;

import com.gymfox.army.Ability.WerewolfAbility;

public class Werewolf extends Unit {
    public Werewolf(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new WerewolfAbility(this);
    }

}
