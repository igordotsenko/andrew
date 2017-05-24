#include "werewolf.h"

using namespace std;

Werewolf::Werewolf(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage) {
    ability = new WolfAbility(this);
    normalState = new HumanState(this);
    wolfState = new WolfState(this);
    unitType = werewolfType;
}

Werewolf::~Werewolf() {
    delete normalState;
}
