#include "necromancer.h"

Necromancer::Necromancer(const string& name, int healthPoint, int damage, int manaPoint) : Spellcaster(name, healthPoint, damage, manaPoint) {
    setAbility(new NecroAbility(this));
    setCurrentSpell("Fireball");
    setCurrentState(new HumanState(this));
    setNextState(new HumanState(this));
    setUnitType(necromancer);
    setIsDead();
}

Necromancer::~Necromancer() {}

void Necromancer::castSpell(Unit* victim) {
    if ( victim->getUnitType() == berserk ) {
        return;
    }
    addObservable(victim);
    Spellcaster::castSpell(victim);
}

void Necromancer::attack(Unit* victim) {
    addObservable(victim);
    Unit::attack(victim);
}
