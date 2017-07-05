#include "vampire.h"

using namespace std;

Vampire::Vampire(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage)  {
    setAbility(new VampireAbility(this));
    setCurrentState(new HumanState(this));
    setNextState(new HumanState(this));
    setUnitType(vampire);
    setIsDead();
}

Vampire::~Vampire() {}
