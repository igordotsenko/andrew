#include "batstate.h"

BatState::BatState(Unit* currentStateUnit) : State(currentStateUnit) {
    this->name = "Bat";
    this->healthPointsLimit = currentStateUnit->getHPLimit() / 2;
    this->currentHP = currentStateUnit->getCurrentHP() / 2;
    this->damage = currentStateUnit->getDamage() * 2;
}

BatState::~BatState() {}

void BatState::vampirism(Unit* victim) {
    int recoverHP = victim->getCurrentHP() / 4;
    victim->takeDamage(recoverHP);
    currentStateUnit->heal(recoverHP);
}