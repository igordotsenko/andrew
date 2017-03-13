#include "unit.h"

using namespace std;

Unit::Unit(const string& name, int healthPoint, int damage) {
    this -> name = name;
    this -> healthPointLimit = healthPoint;
    this -> currentHealthPoint = healthPoint;
    this -> damage = damage;
}

Unit::~Unit() {}

void Unit::attack(Unit* victim) {
    ensureIsAlive();
    victim -> takeDamage(getDamage());

    cout << "\t" << getName() << " taked " << getDamage() << " damage." << endl;

    victim -> counterAttack(this);
}

void Unit::takeDamage(int damage) {
    ensureIsAlive();
    currentHealthPoint -= damage;

    if ( currentHealthPoint <= 0 ) {
        currentHealthPoint = 0;
    }
}
/*
void Unit::takeMagicDamage(int damage) {
    ensureIsAlive();
}
*/
void Unit::counterAttack(Unit* victim) {
    ensureIsAlive();

    victim -> takeDamage(getDamage()/2);
    cout << "\t" << getName() << " counterattacking and taked " << getDamage()/2 << " damage.\n" << endl;
}

const string& Unit::getName() const{
    return name;
}

int Unit::getDamage() const{
    return damage;
}

int Unit::getCurrentHealthPoint() const{
    return currentHealthPoint;
}

int Unit::getHealthtPointsLimit() const{
    return healthPointLimit;
}

void Unit::ensureIsAlive() {
    if ( currentHealthPoint == 0 ) {
        throw UnitIsDeadException();
    }
}

ostream& operator<<(ostream& out, const Unit& unit) {
    out << unit.getName() << " : " << unit.getHealthtPointsLimit() << "/" << unit.getCurrentHealthPoint()  << endl;
    out << "DMG: " << unit.getDamage() << "\n" << endl;

    return out;
}
