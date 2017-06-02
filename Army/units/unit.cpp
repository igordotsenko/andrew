#include "unit.h"

using namespace std;

Unit::Unit(const string& name, int healthPoint, int damage) {
    this->name = name;
    this->healthPointLimit = healthPoint;
    this->currentHP = healthPoint;
    this->damage = damage;
    this->isFriendly = false;
}

Unit::~Unit() {}

void Unit::attack(Unit* victim) {
    ensureIsNotSelfAttack(victim);
    ensureIsNotAlly(victim);
    ensureIsAlive();
    ability->attack(victim);
}

void Unit::takeDamage(int damage) {
    ensureIsAlive();

    if ( getCurrentHP() < damage ) {
        setCurrentHP(0);

        return;
    }
    
    setCurrentHP(getCurrentHP() - damage);
}

void Unit::takeMagicDamage(int damage) {
    ensureIsAlive();
    
    ability->takeMagicDamage(damage);
}

void Unit::changeState() {
    ability->changeState();
}

void Unit::counterAttack(Unit* victim) {
    ensureIsAlive();
    victim->ability->counterAttack(this);
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

void Unit::setFriendly() {
    isFriendly = !isFriendly;
}

void Unit::setCurrentState(State* newCurrentState) {
    normalState = newCurrentState;
}

void Unit::setNextState(State* newNextState) {
    wolfState = newNextState;
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

bool Unit::isAlly() const {
    return isFriendly;
}

State* Unit::getCurrentState() const {
    return normalState;
}

State* Unit::getNextState() const {
    return wolfState;
}

const int Unit::getUnitType() const {
    return unitType;
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

void Unit::ensureIsNotAlly(Unit* target) {
    if ( this->isAlly() == target->isAlly() ) {
        throw IsFriendlyAttackException() ;
    }
}

ostream& operator<<(ostream& out, const Unit& unit) {
    out << unit.getName() << "\nHP: " << unit.getHPLimit() << "|" << unit.getCurrentHP() << endl;
    out << "DMG: " << unit.getDamage() << "\n";

    return out;
}
