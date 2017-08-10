package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.WerewolfAbility;

public class Werewolf extends Unit {
    public Werewolf(String name, int healthPointLimit, int damage) {
        super(name, healthPointLimit, damage);
        this.ability = new WerewolfAbility(this);
    }

}
