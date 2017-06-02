#include "rogue.h"

using namespace std;

Rogue::Rogue(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage)  {
    ability = new RogueAbility(this);
    unitType = rogueType;
}

Rogue::~Rogue() {}

void Rogue::attack(Unit* victim) {
    ensureIsAlive();
    ability->attack(victim);
}