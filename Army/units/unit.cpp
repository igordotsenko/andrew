#include "unit.h"

using namespace std;

Unit::Unit(const string& name, int healthPoint, int damage) {
    this->name = name;
    this->healthPointLimit = healthPoint;
    this->currentHP = healthPoint;
    this->damage = damage;
    this->isDead = false;
}

Unit::~Unit() {
    delete ability;
    delete normalState;
    delete alternativeState;
}

void Unit::attack(Unit* victim) {
    ensureIsNotSelfAttack(victim);
    ensureIsAlive();
    getAbility()->attack(victim);
}

void Unit::takeDamage(int damage) {
    ensureIsAlive();

    getAbility()->takeDamage(damage);
/*
    if ( getCurrentHP() <= damage ) {
        
        setCurrentHP(0);
        notifyObservers();
        notifyObservable();
        
        return;
    }
    
    setCurrentHP(getCurrentHP() - damage);
*/
}

void Unit::takeMagicDamage(int damage) {
    ensureIsAlive();
    
    getAbility()->takeMagicDamage(damage);
}

void Unit::changeState() {
    getAbility()->changeState();
}

void Unit::counterAttack(Unit* victim) {
    ensureIsAlive();
    victim->getAbility()->counterAttack(this);
}

void Unit::setHPLimit(int newHPLimit) {
    healthPointLimit = newHPLimit;
}

void Unit::setCurrentHP(int newCurrentHP) {
    currentHP = newCurrentHP;
}

void Unit::setDamage(int newDamage) {
    damage = newDamage;
}

void Unit::setAbility(Ability* newAbility) {
    ability = newAbility;
}

void Unit::setCurrentState(State* newCurrentState) {
    normalState = newCurrentState;
}

void Unit::setNextState(State* newNextState) {
    alternativeState = newNextState;
}

void Unit::setUnitType(int newUnitType) {
    unitType = newUnitType;
}

void Unit::setIsDead() {
    isDead = !isDead;
}

const string& Unit::getName() const {
    return name;
}

int Unit::getDamage() const {
    return damage;
}

int Unit::getCurrentHP() const {
    return currentHP;
}

int Unit::getHPLimit() const {
    return healthPointLimit;
}

bool Unit::getIsDead() const {
    return isDead;
}

Ability* Unit::getAbility() const {
    return ability;
}

State* Unit::getCurrentState() const {
    return normalState;
}

State* Unit::getNextState() const {
    return alternativeState;
}

const int Unit::getUnitType() const {
    return unitType;
}

const set<Observer*>& Unit::getObservers() const {
    return observers;
}

const set<Observable*>& Unit::getObservables() const {
    return observables;
}

void Unit::notifyObservers() {
    for ( set<Observer*>::iterator it = observers.begin(); it != observers.end(); it++ ) {
        ((Unit*)(*it))->heal(((Unit*)(*it))->getHPLimit()/10);
        (*it)->removeObservable(this);
    }
}

void Unit::notifyObservable() {
    for ( set<Observable*>::iterator it = observables.begin(); it != observables.end(); it++ ) {
        (*it)->removeObserver(this);
    }
}


void Unit::heal(int healthPoint) {
    ensureIsAlive();

    int newCurrentHP = getCurrentHP() + healthPoint;

    if ( newCurrentHP > getHPLimit() ) {
        setCurrentHP(getHPLimit());
        
        return;
    }

    setCurrentHP(newCurrentHP);
}

void Unit::ensureIsAlive() {
    if ( getCurrentHP() == 0 ) {
        throw UnitIsDeadException();
    }
}

void Unit::ensureIsNotSelfAttack(Unit* victim) {
    if ( this == victim ) {
        throw IsSelfAttackException();
    }
}

ostream& operator<<(ostream& out, const Unit& unit) {
    out << unit.getName() << "\nHP: " << unit.getHPLimit() << "|" << unit.getCurrentHP() << endl;
    out << "DMG: " << unit.getDamage() << "\n";
    out << unit.getCurrentState()->getStateName() << endl;

    return out;
}
