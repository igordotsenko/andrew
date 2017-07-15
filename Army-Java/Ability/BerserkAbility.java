package com.gymfox.Army.Ability;

import com.gymfox.Army.Units.Unit;

public class BerserkAbility extends Ability {
    public BerserkAbility(Unit currentUnit) {
        super(currentUnit);
    }

    @Override
    public void takeMagicDamage(int damage) {}
}
