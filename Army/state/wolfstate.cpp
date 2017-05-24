#include "wolfstate.h"

using namespace std;

WolfState::WolfState(Unit* currentStateUnit) : State(currentStateUnit) {
    this->healthPointsLimit = currentStateUnit->getHPLimit() * 2;
    this->currentHP = currentStateUnit->getCurrentHP() * 2;
    this->damage = currentStateUnit->getDamage() * 2;
}

WolfState::~WolfState() {}

void WolfState::takeMagicDamage(int damage) {
    int newHP = currentStateUnit->getCurrentHP();
    int newDamage = damage * 2;    

    if ( currentHP <= 0 ) {
        currentStateUnit->setCurrentHP(0);

        return;
    }
    
    currentStateUnit->setCurrentHP(newHP - newDamage);
}
