#include "state.h"

using namespace std;

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

int State::getHPLimit() const {
    return healthPointsLimit;
}

int State::getCurrentHP() const {
    return currentHP;
}


int State::getDamage() const {
    return damage;
}
