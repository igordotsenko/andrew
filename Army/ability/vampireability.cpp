#include "vampireability.h"

VampireAbility::VampireAbility(Unit* currentUnit) : Ability(currentUnit) {}

VampireAbility::~VampireAbility() {};

void VampireAbility::attack(Unit* victim) {
    victim->takeDamage(currentUnit->getDamage());

    currentUnit->getCurrentState()->vampirism(victim);

    victim->counterAttack(currentUnit);
}
