#include "rogue.h"

using namespace std;

Rogue::Rogue(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage)  {
    setAbility(new RogueAbility(this));
    setCurrentState(new HumanState(this));
    setNextState(new HumanState(this));
    setUnitType(rogue);
}

Rogue::~Rogue() {}