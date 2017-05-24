#include "vampire.h"

using namespace std;

Vampire::Vampire(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage) {
    ability = new VampireAbility(this);
    unitType = vampireType;
}

Vampire::~Vampire() {}
