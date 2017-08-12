package com.gymfox.Army.Ability;

import com.gymfox.Army.State.State;
import com.gymfox.Army.Units.Unit;

public abstract class Ability {
    private Unit currentUnit;
    protected State currentState;
    protected State nextState;

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

    public void takeMagicDamage(int damage) throws Unit.UnitIsDeadException {
        if ( currentUnit.getCurrentHP() <= damage ) {
            currentUnit.setCurrentHP(0);
            currentUnit.notifyObservers();
            currentUnit.notifyObservable();

            return;
        }
        if ( currentUnit.getIsDead() ) {
            currentUnit.setCurrentHP(currentUnit.getCurrentHP() - damage * 4);

            return;

        }
        currentUnit.setCurrentHP(currentUnit.getCurrentHP() - damage);
    }

    public void changeState() {}

    public Unit getCurrentUnit() {
        return currentUnit;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }
}