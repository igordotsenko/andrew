    #include "werewolf.h"

using namespace std;

Werewolf::Werewolf(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage)  {
    setAbility(new WolfAbility(this));
    setCurrentState(new HumanState(this));
    setNextState(new WolfState(this));
    setUnitType(werewolf);
}

Werewolf::~Werewolf() {}
