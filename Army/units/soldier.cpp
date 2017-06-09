#include "soldier.h"


using namespace std;

Soldier::Soldier(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage) {
    setAbility(new DefaultAbility(this));
    setCurrentState(new HumanState(this));
    setNextState(new HumanState(this));
    setUnitType(soldier);
}

Soldier::~Soldier() {}