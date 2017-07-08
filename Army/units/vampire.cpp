#include "vampire.h"

using namespace std;

Vampire::Vampire(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage)  {
    setAbility(new VampireAbility(this));
    setCurrentState(new HumanState(this));
    setNextState(new BatState(this));
    setUnitType(vampire);
    setIsDead();
}

Vampire::~Vampire() {}
