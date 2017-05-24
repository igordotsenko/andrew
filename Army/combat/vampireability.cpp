#include "vampireability.h"

using namespace std;

VampireAbility::VampireAbility(Unit* currentUnit) : Ability(currentUnit) {}

VampireAbility::~VampireAbility() {};

void VampireAbility::attack(Unit* victim) {
    victim->takeDamage(currentUnit->getDamage());
    vampirism(victim);

    victim->counterAttack(currentUnit);
}

void VampireAbility::counterAttack(Unit* victim) {
    currentUnit->takeDamage(victim->getDamage() / 2);
    vampirism(victim);
}

void VampireAbility::vampirism(Unit* victim) {
    int recoverHP = victim->getCurrentHP() / 10;
    currentUnit->heal(recoverHP);
}