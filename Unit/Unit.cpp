#include <iostream>

#include "Unit.h"

using namespace std;

void Unit::ensureIsAlive() {
   if ( hitPoints == 0 ) {
       throw UnitIsDeadException();
   }
}

Unit::Unit(const std::string& name, int hp, int dmg) {
	this->name = name;
	this->hitPoints = hp;
	this->hitPointsLimit = hp;
	this->damage = dmg;
}

Unit::~Unit() {}

int Unit::getDamage() const {
	return damage;
}

int Unit::getHitPoints() const {
	return hitPoints;
}

int Unit::getHitPointsLimit() const {
	return hitPointsLimit;
}

const std::string& Unit::getName() const {
	return name;
}

void Unit::addHitPoints(int hp) {
	ensureIsAlive();

	if ( hitPointsLimit - hitPoints < hp ) {
		hitPoints = hitPointsLimit;
	} else {
		hitPoints += hp;
	}
}

void Unit::takeDamage(int dmg) {
	ensureIsAlive();

	hitPoints -= dmg;
	if ( hitPoints <= 0 ) {
		hitPoints = 0;
		throw UnitIsDeadException();
	}
}

void Unit::attack(Unit& enemy) {
	enemy.takeDamage(damage);
	cout << getName() << " taked " << damage << " damage." << endl;

	ensureIsAlive();

	enemy.counterAttack(*this);
}

void Unit::counterAttack(Unit& enemy) {
	enemy.takeDamage(damage/2);
	cout << getName() << " counterattacking " << enemy.getName() << " and taked " << damage/2 << " damage." << endl;
}

ostream& operator<<(ostream& out, const Unit& unit) {
	out << "Unit name: " << unit.getName() << endl;
	out << "Health: " << unit.getHitPointsLimit() << "/" << unit.getHitPoints() << endl;
	out << "Unit's damage: " << unit.getDamage() << endl;

	return out;
}
