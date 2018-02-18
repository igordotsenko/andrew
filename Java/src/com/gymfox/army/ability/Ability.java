package com.gymfox.army.Ability;

import com.gymfox.army.Units.Unit;

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
        tryToKill(damage);
    }

    public void takeMagicDamage(int damage) throws Unit.UnitIsDeadException {
        tryToKill(damage);
    }

    private void tryToKill(int damage) throws Unit.UnitIsDeadException {
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