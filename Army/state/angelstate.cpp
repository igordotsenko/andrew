#include "angelstate.h"

AngelState::AngelState(Unit* currentStateUnit) : State(currentStateUnit) {
    this->name = "Angel";
    this->healthPointsLimit = currentStateUnit->getHPLimit() * 2;
    this->currentHP = currentStateUnit->getCurrentHP() * 2;
    this->damage = currentStateUnit->getDamage();
}

AngelState::~AngelState() {}

void AngelState::takeDamage(int damage) {
    int newDamage = damage / 2;

    if ( currentStateUnit->getCurrentHP() <= newDamage ) {
        currentStateUnit->setCurrentHP(0);
        currentStateUnit->notifyObservers();
        currentStateUnit->notifyObservable();

        return;
    }
    
    currentStateUnit->setCurrentHP(currentStateUnit->getCurrentHP() - newDamage);
}

void AngelState::takeMagicDamage(int damage) {
    int newDamage = damage / 2;

    if ( currentStateUnit->getCurrentHP() <= newDamage ) {
        currentStateUnit->setCurrentHP(0);
        currentStateUnit->notifyObservers();
        currentStateUnit->notifyObservable();
        
        return;
    }
    
    currentStateUnit->setCurrentHP(currentStateUnit->getCurrentHP() - newDamage);
}