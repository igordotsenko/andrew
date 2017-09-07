package com.gymfox.Army.Ability;

import com.gymfox.Army.Units.Unit;

public abstract class Ability {
    private Unit currentUnit;

    public Ability(Unit currentUnit) {
        this.currentUnit = currentUnit;
    }

    public void attack(Unit victim) throws Unit.UnitIsDeadException, Unit.IsSelfAttackException {
        victim.takeDamage(currentUnit.getDamage());
        victim.counterAttack(currentUnit);
    }

    public void counterAttack(Unit victim) throws Unit.UnitIsDeadException {
        victim.takeDamage(getCurrentUnit().getDamage() / 2);
    }

    public void takeDamage(int damage) throws Unit.UnitIsDeadException {
        if ( currentUnit.getCurrentHP() <= damage ) {
            currentUnit.setCurrentHP(0);
            currentUnit.notifyObservers();
            currentUnit.notifyObservable();

            return;
        }

        currentUnit.setCurrentHP(currentUnit.getCurrentHP() - damage);
    }

    public void takeMagicDamage(int damage) throws Unit.UnitIsDeadException {
        if ( currentUnit.getCurrentHP() <= damage ) {
            currentUnit.setCurrentHP(0);
            currentUnit.notifyObservers();
            currentUnit.notifyObservable();

            return;
        }

        currentUnit.setCurrentHP(currentUnit.getCurrentHP() - damage);
    }

    public void changeState() {}

    public Unit getCurrentUnit() {
        return currentUnit;
    }
}