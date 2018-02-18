package com.gymfox.army.Ability;

import com.gymfox.army.Units.Unit;

public class VampireAbility extends Ability {
    public VampireAbility(Unit currentUnit) {
        super(currentUnit);
    }

    @Override
    public void attack(Unit victim) throws Unit.UnitIsDeadException, Unit.IsSelfAttackException {
        victim.takeDamage(getCurrentUnit().getDamage());

        vampirism(victim);

        victim.counterAttack(getCurrentUnit());
    }

    @Override
    public void counterAttack(Unit victim) throws Unit.UnitIsDeadException {
        victim.takeDamage(getCurrentUnit().getDamage() / 2);

        int recoverHP = victim.getCurrentHP() / 10;

        getCurrentUnit().heal(recoverHP);
        victim.takeDamage(recoverHP);
    }

    public void vampirism(Unit victim) throws Unit.UnitIsDeadException {
        int recoverHP = victim.getCurrentHP() / 10;

        victim.takeDamage(recoverHP);
        getCurrentUnit().heal(recoverHP);
    }
}
