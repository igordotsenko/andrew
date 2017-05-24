#include "wolfability.h"

WolfAbility::WolfAbility(Unit* currentUnit) : Ability(currentUnit) {}

WolfAbility::~WolfAbility() {}

void WolfAbility::changeState() {
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

double WolfAbility::getHealthMultiplier() const {
    return (double)currentUnit->getCurrentHP() / (double)currentUnit->getHPLimit();
}