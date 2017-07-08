#include "state.h"

State::State(Unit* currentStateUnit) {
    this->currentStateUnit = currentStateUnit;
}

State::~State() {}

void State::takeDamage(int damage) {

    if ( currentStateUnit->getCurrentHP() <= damage ) {
        kill();
        ensureIsAngelState();

        return;
    }
    
    currentStateUnit->setCurrentHP(currentStateUnit->getCurrentHP() - damage);
}

void State::takeMagicDamage(int damage) {
    if ( currentStateUnit->getCurrentHP() <= damage ) {
        kill();
        ensureIsAngelState();

        return;
    }

    currentStateUnit->setCurrentHP(currentStateUnit->getCurrentHP() - damage);
}

void State::kill() {
        currentStateUnit->setCurrentHP(0);
        currentStateUnit->notifyObservers();
        currentStateUnit->notifyObservable();
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

void State::ensureIsAngelState() {
    if ( currentStateUnit->getNextState()->getStateName() == "Angel" ) {
        currentStateUnit->changeState();

        currentStateUnit->setCurrentHP(currentStateUnit->getHPLimit());

        return;
    }
}

const string& State::getStateName() const {
    return name;
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
