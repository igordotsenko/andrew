package com.gymfox.army.ability;

import com.gymfox.army.units.Unit;

public class BerserkAbility extends Ability {
    public BerserkAbility(Unit currentUnit) {
        super(currentUnit);
    }

    @Override
    public void takeMagicDamage(int damage) {}
}
