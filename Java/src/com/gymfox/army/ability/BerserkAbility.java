package com.gymfox.army.Ability;

import com.gymfox.army.Units.Unit;

public class BerserkAbility extends Ability {
    public BerserkAbility(Unit currentUnit) {
        super(currentUnit);
    }

    @Override
    public void takeMagicDamage(int damage) {}
}
