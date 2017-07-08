#include "humanstate.h"

HumanState::HumanState(Unit* currentStateUnit) : State(currentStateUnit) {
    this->name = "Human";
    this->healthPointsLimit = currentStateUnit->getHPLimit();
    this->currentHP = currentStateUnit->getHPLimit();
    this->damage = currentStateUnit->getDamage();
}

HumanState::~HumanState() {}