#include "rogue.h"

using namespace std;

Rogue::Rogue(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage) {}

Rogue::~Rogue() {}

void Rogue::attack(Unit* victim) {
    victim->takeDamage(getDamage());
}