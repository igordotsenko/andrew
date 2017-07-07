#include "ability.h"

Ability::Ability(Unit* currentUnit) : currentUnit(currentUnit) {}

Ability::~Ability() {}

void Ability::attack(Unit* victim) {
    victim->takeDamage(currentUnit->getDamage());
    victim->counterAttack(currentUnit);
}

void Ability::counterAttack(Unit* victim) {
    currentUnit->takeDamage(victim->getDamage() / 2);
    
    if ( victim->getUnitType() == vampire ) {
        victim->getCurrentState()->vampirism(currentUnit);
    }
}

void Ability::takeMagicDamage(int damage) {
    currentUnit->getCurrentState()->takeMagicDamage(damage);
}

void Ability::castSpell(Unit* victim, Spell* spell) {}

void Ability::changeState() {
    State* currentState = currentUnit->getCurrentState();
    State* nextState = currentUnit->getNextState();
    State* tmp = currentState;

    currentUnit->setCurrentState(nextState);
    currentUnit->setNextState(tmp);
    currentState = nextState;

    int newCurrentHP = (getHealthMultiplier() * (double)currentState->getHPLimit());

    currentUnit->setHPLimit(currentState->getHPLimit());
    currentUnit->setCurrentHP(newCurrentHP); 
    currentUnit->setDamage(currentState->getDamage());
}

double Ability::getHealthMultiplier() const {
    return (double)currentUnit->getCurrentHP() / (double)currentUnit->getHPLimit();
}
