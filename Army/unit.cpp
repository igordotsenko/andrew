#include "unit.h"

using namespace std;

Unit::Unit(const string& name, int healthPoint, int damage) {
    this->name = name;
    this->healthPointLimit = healthPoint;
    this->currentHP = healthPoint;
    this->damage = damage;
}

Unit::~Unit() {}

void Unit::attack(Unit* victim) {
    ensureIsAlive();
    victim->takeDamage(getDamage());

    cout << "\t" << getName() << " hit " << victim->getName() << " for " << getDamage() << " damage." << endl;

    victim->counterAttack(this);
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
    
    if ( getCurrentHP() < damage ) {
        setCurrentHP(0);

        return;
    }
    
    setCurrentHP(getCurrentHP() - damage);
}

void Unit::counterAttack(Unit* victim) {
    ensureIsAlive();

    victim->takeDamage(getDamage() / 2);
    cout << "\t" << getName() << " is counterattacking and hit " << victim->getName() << " for " << getDamage() / 2 << " damage.\n" << endl;
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

ostream& operator<<(ostream& out, const Unit& unit) {
    out << unit.getName() << "\nHP: " << unit.getHPLimit() << "|" << unit.getCurrentHP() << endl;
    out << "DMG: " << unit.getDamage() << "\n";

    return out;
}
