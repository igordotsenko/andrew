#include "healer.h"

using namespace std;

Healer::Healer(const string& name, int healthPoint, int damage, int manaPoint) : Spellcaster(name, healthPoint, damage, manaPoint) {
    setAbility(new HealerAbility(this));
    setUnitType(healer);
    setCurrentSpell("Heal");
    setCurrentState(new HumanState(this));
    setNextState(new HumanState(this));
}

Healer::~Healer() {}