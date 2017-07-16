package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.Ability;
import com.gymfox.Army.Exception.IsSelfAttackException;
import com.gymfox.Army.Exception.MasterAttackedException;
import com.gymfox.Army.Exception.UnitIsDeadException;
import com.gymfox.Army.Observer.Observable;
import com.gymfox.Army.Observer.Observer;
import com.gymfox.Army.State.State;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class Unit implements Observable, Observer {

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
    private Set<Unit> observables = new HashSet<>();
    private Set<Unit> observers = new HashSet<>();

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

    public void notifyObservers() throws UnitIsDeadException {
        for ( Unit observer: observers ) {
            observer.removeObservable(this);
            observer.heal(observer.getDamage());
        }
    }

    public void notifyObservable() {
        for ( Unit observable: observables ) {
            observable.removeObserver(this);
        }
    }

    public String toString() {
        StringBuffer out = new StringBuffer();

        out.append("Name: " + getName() + "\n");
        out.append("HP: " + getHealthPointLimit() + " | " + getCurrentHP() + "\n");
        out.append("DMG: " + getDamage() + "\n");

        if ( !observables.isEmpty() ) {
            out.append("Observable: [ ");

            for ( Unit observable : observables ) {
                out.append(observable.getName()+ " ");
            }

            out.append("]\n");
        }

        if ( !observers.isEmpty() ) {
            out.append("Observers: [ ");

            for ( Unit observer : observers ) {
                out.append(observer.getName() + " ");
            }
            out.append("]\n");
        }

        return out.toString();
    }

    @Override
    public void addObservable(Unit observable) {
        observables.add(observable);
        observable.addObserver(this);
    }

    @Override
    public void addObserver(Unit observer) {
        observers.add(observer);
    }

    @Override
    public void removeObservable(Unit observable) {
        observables.remove(observable);
    }

    @Override
    public void removeObserver(Unit observer) {
        observers.remove(observer);
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

    public Set<Unit> getObservables() {
        return observables;
    }

    public Set<Unit> getObservers() {
        return observers;
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
