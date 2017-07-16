package com.gymfox.Army.State;

import com.gymfox.Army.Exception.UnitIsDeadException;
import com.gymfox.Army.Units.Unit;

public abstract class State {
    protected Unit currentUnitState;
    protected String stateName;
    protected int healthPointLimit;
    protected int damage;

    public State(Unit currentUnitState) {
        this.currentUnitState = currentUnitState;
        this.healthPointLimit = currentUnitState.getHealthPointLimit();
        this.damage = currentUnitState.getDamage();
    }

    public void takeDamage(int damage) throws UnitIsDeadException {
        tryKill(damage);
    }

    public void takeMagicDamage(int damage) throws UnitIsDeadException {
        tryKill(damage);
    }

    public void tryKill(int damage) throws UnitIsDeadException {
        if ( currentUnitState.getCurrentHP() <= damage ) {
            currentUnitState.setCurrentHP(0);
            currentUnitState.notifyObservers();
            currentUnitState.notifyObservable();

            return;
        }

        currentUnitState.setCurrentHP(currentUnitState.getCurrentHP() - damage);
    }

    public Unit getCurrentUnitState() {
        return currentUnitState;
    }

    public String getStateName() {
        return stateName;
    }

    public int getHealthPointLimit() {
        return healthPointLimit;
    }

    public int getDamage() {
        return damage;
    }

    public void setCurrentUnitState(Unit newCurrentUnitState) {
        this.currentUnitState = newCurrentUnitState;
    }

    public void setStateName(String newStateName) {
        this.stateName = newStateName;
    }

    public void setHealthPointLimit(int newHealthPointLimit) {
        this.healthPointLimit = newHealthPointLimit;
    }

    public void setDamage(int newDamage) {
        this.damage = newDamage;
    }
}
