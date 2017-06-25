#include "vampireability.h"

using namespace std;

VampireAbility::VampireAbility(Unit* currentUnit) : Ability(currentUnit) {}

VampireAbility::~VampireAbility() {};

void VampireAbility::attack(Unit* victim) {
    victim->takeDamage(currentUnit->getDamage());
    vampirism(victim);

    victim->counterAttack(currentUnit);
}

void VampireAbility::vampirism(Unit* victim) {
    int recoverHP = victim->getCurrentHP() / 10;
    victim->takeDamage(recoverHP);
    currentUnit->heal(recoverHP);
}