#include "priest.h"

using namespace std;

Priest::Priest(const string& name, int healthPoint, int damage, int manaPoint) : Spellcaster(name, healthPoint, damage, manaPoint) {
    setAbility(new PriestAbility(this));
    setUnitType(priest);
    setCurrentSpell("Heal");
    setCurrentState(new HumanState(this));
    setNextState(new HumanState(this));
}

Priest::~Priest() {}