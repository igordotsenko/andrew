#include "state.h"

State::State(Unit* currentStateUnit) {
    this->currentStateUnit = currentStateUnit;
}

State::~State() {}

void State::takeMagicDamage(int damage) {
    if ( currentStateUnit->getCurrentHP() <= damage ) {
        currentStateUnit->setCurrentHP(0);
        currentStateUnit->notifyObservers();
        currentStateUnit->notifyObservable();

        return;
    }

    currentStateUnit->setCurrentHP(currentStateUnit->getCurrentHP()-damage);
}

void State::vampirism(Unit* victim) {
        ensureIsVampireType();

        int recoverHP = victim->getCurrentHP() / 10;
        victim->takeDamage(recoverHP);
        currentStateUnit->heal(recoverHP);
}

void State::ensureIsVampireType() {
    if ( currentStateUnit->getUnitType() != vampire ) {
        throw IsNotVampireTypeException();
    }
}

int State::getHPLimit() const {
    return healthPointsLimit;
}

int State::getCurrentHP() const {
    return currentHP;
}

int State::getDamage() const {
    return damage;
}
