package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.Ability;
import com.gymfox.Army.Exception.IsSelfAttackException;
import com.gymfox.Army.Exception.MasterAttackedException;
import com.gymfox.Army.Exception.UnitIsDeadException;
import com.gymfox.Army.State.State;

public abstract class Unit {

    public enum UnitType {
        SOLDIER,
        ROGUE,
        BERSERK,
        VAMPIRE,
        WEREWOLF,
        WIZARD,
        HEALER,
        PRIEST,
        WARLOCK,
        NECROMANCER
    }

    private String name;
    private int healthPointLimit;
    private int currentHP;
    private int damage;
    private boolean isDead = false;
    private UnitType unitType;
    private Ability ability;
    private State currentState;
    private State nextState;

    public Unit(String name, int healthPointLimit, int damage) {
        this.name = name;
        this.healthPointLimit = healthPointLimit;
        this.currentHP = healthPointLimit;
        this.damage = damage;
    }

    public void attack(Unit victim) throws UnitIsDeadException, IsSelfAttackException, MasterAttackedException {
        ensureIsNotSelfAttack(victim);
        ensureIsAlive();

        getAbility().attack(victim);
    }

    public void counterAttack(Unit victim) throws UnitIsDeadException, IsSelfAttackException {
        ensureIsNotSelfAttack(victim);
        ensureIsAlive();

        getAbility().counterAttack(victim);
    }

    public void takeDamage(int damage) throws UnitIsDeadException {
        ensureIsAlive();
        getAbility().takeDamage(damage);
    }

    public void takeMagicDamage(int damage) throws UnitIsDeadException {
        ensureIsAlive();
        getAbility().takeMagicDamage(damage);
    }

    public void heal(int healthPoint) throws UnitIsDeadException {
        ensureIsAlive();

        int newHealthPoint = getCurrentHP() + healthPoint;

        if ( getHealthPointLimit() <= newHealthPoint ) {
            setCurrentHP(getHealthPointLimit());

            return;
        }

        setCurrentHP(newHealthPoint);
    }

    protected void ensureIsAlive() throws UnitIsDeadException {
        if ( getCurrentHP() == 0 ) {
            throw new UnitIsDeadException();
        }
    }

    protected void ensureIsNotSelfAttack(Unit victim) throws IsSelfAttackException {
        if ( this == victim ) {
            throw new IsSelfAttackException();
        }
    }

    public void changeState() {
        getAbility().changeState();
    }

    public String toString() {
        StringBuffer out = new StringBuffer();

        out.append("Name: " + getName() + "\n");
        out.append("HP: " + getHealthPointLimit() + " | " + getCurrentHP() + "\n");
        out.append("DMG: " + getDamage() + "\n");

        return out.toString();
    }

    public String getName() {
        return name;
    }

    public int getHealthPointLimit() {
        return healthPointLimit;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getDamage() {
        return damage;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public Ability getAbility() {
        return ability;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public State getCurrentState() {
        return currentState;
    }

    public State getNextState() {
        return nextState;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setHealthPointLimit(int newHealthPointLimit) {
        this.healthPointLimit = newHealthPointLimit;
    }

    public void setCurrentHP(int newCurrentHP) {
        this.currentHP = newCurrentHP;
    }

    public void setDamage(int newDamage) {
        this.damage = newDamage;
    }

    public void setDead() {
        isDead = !isDead;
    }

    public void setUnitType(UnitType newUnitType) {
        this.unitType = newUnitType;
    }

    public void setAbility(Ability newAbility) {
        this.ability = newAbility;
    }

    public void setCurrentState(State newCurrentState) {
        this.currentState = newCurrentState;
    }

    public void setNextState(State newNextState) {
        this.nextState = newNextState;
    }
}
