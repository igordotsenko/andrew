#include "rogueability.h"

RogueAbility::RogueAbility(Unit* currentUnit) : Ability(currentUnit) {}

RogueAbility::~RogueAbility() {}

void RogueAbility::attack(Unit* victim) {
    victim->takeDamage(currentUnit->getDamage());
}