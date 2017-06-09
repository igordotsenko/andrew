#include "berserk.h"

using namespace std;

Berserk::Berserk(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage)  {
    setAbility(new BerserkAbility(this));
    setCurrentState(new HumanState(this));
    setNextState(new HumanState(this));
    setUnitType(berserk);
}

Berserk::~Berserk() {}

void Berserk::takeMagicDamage(int damage) {}