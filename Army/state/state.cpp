#include "state.h"

using namespace std;

State::State(Unit* currentStateUnit) {
    this->currentStateUnit = currentStateUnit;
}

State::~State() {}

void State::takeMagicDamage(int damage) {
    if ( currentHP <= 0 ) {
        currentStateUnit->setCurrentHP(0);

        return;
    }
    
    currentStateUnit->setCurrentHP(currentHP - damage);
}

int State::getHPLimit() const {
    return healthPointsLimit;
}

int State::getDamage() const {
    return damage;
}
