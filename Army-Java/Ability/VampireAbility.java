package com.gymfox.Army.Ability;

import com.gymfox.Army.Exception.*;
import com.gymfox.Army.Units.Unit;

public class VampireAbility extends Ability {
    public VampireAbility(Unit currentUnit) {
        super(currentUnit);
    }

    @Override
    public void attack(Unit victim) throws UnitIsDeadException, IsSelfAttackException {
        victim.takeDamage(getCurrentUnit().getDamage());

        vampirism(victim);

        victim.counterAttack(getCurrentUnit());
    }

    @Override
    public void counterAttack(Unit victim) throws UnitIsDeadException {
        victim.takeDamage(getCurrentUnit().getDamage() / 2);
        
        if ( getCurrentUnit().getUnitType() == Unit.UnitType.VAMPIRE ) {
            int recoverHP = victim.getCurrentHP() / 10;

            getCurrentUnit().heal(recoverHP);
            victim.takeDamage(recoverHP);
        }
    }

    public void vampirism(Unit victim) throws UnitIsDeadException {
        int recoverHP = victim.getCurrentHP() / 10;

        victim.takeDamage(recoverHP);
        getCurrentUnit().heal(recoverHP);
    }
}
