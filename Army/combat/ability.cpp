#include "ability.h"

Ability::Ability(Unit* currentUnit) : currentUnit(currentUnit) {}

Ability::~Ability() {}

void Ability::attack(Unit* victim) {
    victim->takeDamage(currentUnit->getDamage());
    victim->counterAttack(currentUnit);
}

void Ability::counterAttack(Unit* victim) {
    currentUnit->takeDamage(victim->getDamage() / 2);
    
    if ( victim->getUnitType() == vampireType ) {
        victim->heal(currentUnit->getCurrentHP() / 10);
    }
}

void Ability::takeMagicDamage(int damage) {
    currentUnit->getCurrentState()->takeMagicDamage(damage);
}

void Ability::changeState() {
}