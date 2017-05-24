#include "humanstate.h"

HumanState::HumanState(Unit* currentStateUnit) : State(currentStateUnit) {
    this->healthPointsLimit = currentStateUnit->getHPLimit();
    this->currentHP = currentStateUnit->getHPLimit();
    this->damage = currentStateUnit->getDamage();
}

HumanState::~HumanState() {}