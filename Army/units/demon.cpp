#include "demon.h"

Demon::Demon(Warlock* master) : Soldier("Demon", 120, 20) {
    this->master = master;
}

Demon::~Demon() {}

void Demon::attack(Unit* victim) {
    ensureIsAlive();
    ensureIsNotSelfAttack(victim);
    ensureIsNotMaster(victim);
    
    getAbility()->attack(victim);
}

void Demon::ensureIsNotMaster(Unit* target) {
    if ( target == (Unit*)getMaster() ) {
        throw MasterAttackedException();
    }
}

Warlock* Demon::getMaster() const {
    return master;
}