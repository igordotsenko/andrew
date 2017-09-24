package com.gymfox.Army.Units;

import com.gymfox.Army.Ability.Ability;
import com.gymfox.Army.Observer.Observable;
import com.gymfox.Army.Observer.Observer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class Unit implements Observable, Observer {

    public static class UnitIsDeadException extends Exception{};
    public static class IsSelfAttackException extends Exception{};

    private String name;
    private int healthPointLimit;
    private int currentHP;
    private int damage;
    private Set<Observable> observables = new HashSet<>();
    private Set<Observer> observers = new HashSet<>();

    protected Ability ability;
    protected boolean isDead = false;
    protected boolean immunityToMagic = false;

    public Unit(String name, int healthPointLimit, int damage) {
        this.name = name;
        this.healthPointLimit = healthPointLimit;
        this.currentHP = healthPointLimit;
        this.damage = damage;
    }

    public void attack(Unit victim) throws IsSelfAttackException, UnitIsDeadException, Demon.MasterAttackedException {
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

    public void changeState() {
        getAbility().changeState();
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

    @Override
    public String toString() {
        StringBuffer out = new StringBuffer();

        out.append("Name: " + getName() + "\n");
        out.append("HP: " + getHealthPointLimit() + " | " + getCurrentHP() + "\n");
        out.append("DMG: " + getDamage() + "\n");

        if ( !observables.isEmpty() ) {
            out.append("Observable: [");
            Iterator<Observable> it = observables.iterator();

            for ( ; it.hasNext() ; ) {
                Unit observable = (Unit) it.next();
                out.append(observable.getName());

                if ( it.hasNext() ) {
                    out.append(", ");
                }
            }

            out.append("]\n");
        }

        if ( !observers.isEmpty() ) {
            out.append("Observers: [ ");
            Iterator<Observer> it = observers.iterator();
            for ( ; it.hasNext() ; ) {
                Unit observer = (Unit) it.next();
                out.append(observer.getName());

                if ( it.hasNext() ) {
                    out.append(", ");
                }
            }
            out.append("]\n");
        }

        return out.toString();
    }

    @Override
    public void addObservable(Observable observable) {
        observables.add(observable);
        observable.addObserver(this);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObservable(Observable observable) {
        observables.remove(observable);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservable() {
        for ( Observable observable: observables ) {
            observable.removeObserver(this);
        }
    }

    @Override
    public void notifyObservers() throws UnitIsDeadException {
        Iterator<Observer> it = observers.iterator();

        for ( ;it.hasNext(); ) {
            Unit observer = (Unit) it.next();
            observer.removeObservable(this);
            observer.heal(observer.getDamage());
        }
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

    public boolean getImmunityToMagic() {
        return immunityToMagic;
    }

    public Set<Observable> getObservables() {
        return observables;
    }

    public Set<Observer> getObservers() {
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

    public void setAbility(Ability newAbility) {
        this.ability = newAbility;
    }
}
