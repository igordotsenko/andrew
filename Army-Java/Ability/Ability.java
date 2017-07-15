package com.gymfox.Army.Ability;

import com.gymfox.Army.Exception.IsSelfAttackException;
import com.gymfox.Army.Exception.UnitIsDeadException;
import com.gymfox.Army.Spells.Spell;
import com.gymfox.Army.State.State;
import com.gymfox.Army.Units.Unit;

public abstract class Ability {
    private Unit currentUnit;

    public Ability(Unit currentUnit) {
        this.currentUnit = currentUnit;
    }

    public void attack(Unit victim) throws UnitIsDeadException, IsSelfAttackException {
        victim.takeDamage(currentUnit.getDamage());
        victim.counterAttack(currentUnit);
    }

    public void counterAttack(Unit victim) throws UnitIsDeadException {
        victim.takeDamage(currentUnit.getDamage() / 2);

        if ( currentUnit.getUnitType() == Unit.UnitType.VAMPIRE ) {
            int recoverHP = victim.getCurrentHP() / 10;
            currentUnit.heal(recoverHP);
            victim.takeDamage(recoverHP);
        }
    }

    public void takeDamage(int damage) {
        currentUnit.getCurrentState().takeDamage(damage);
    }

    public void takeMagicDamage(int damage) {
        currentUnit.getCurrentState().takeMagicDamage(damage);
    }

    public void castSpell(Unit victim, Spell currentSpell) throws UnitIsDeadException {}

    public void changeState() {
        State currentState = currentUnit.getCurrentState();
        State nextState = currentUnit.getNextState();
        State temp = currentState;

        currentUnit.setCurrentState(nextState);
        currentUnit.setNextState(temp);
        currentState = nextState;

        int newCurrentHP = (int)(getHealthMultiplier() * (double)currentState.getHealthPointLimit());

        currentUnit.setHealthPointLimit(currentState.getHealthPointLimit());
        currentUnit.setCurrentHP(newCurrentHP);
        currentUnit.setDamage(currentState.getDamage());
    }

    public double getHealthMultiplier() {
        return (double)currentUnit.getCurrentHP() / (double)currentUnit.getHealthPointLimit();
    }

    public Unit getCurrentUnit() {
        return currentUnit;
    }
}
