#include "state.h"

using namespace std;

State::State(Unit* currentStateUnit) {
    this->currentStateUnit = currentStateUnit;
}

State::~State() {}

void State::takeMagicDamage(int damage) {
    if ( currentHP < damage ) {
        currentStateUnit->setCurrentHP(0);

        return;
    }
    
    currentStateUnit->setCurrentHP(currentStateUnit->getCurrentHP()-damage);
}

int State::getHPLimit() const {
    return healthPointsLimit;
}

int State::getDamage() const {
    return damage;
}
