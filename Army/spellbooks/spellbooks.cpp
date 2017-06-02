#include "spellbooks.h"

Spellbooks::Spellbooks(const string& name, int manaConsumption, int hitPoints) {}

Spellbooks::~Spellbooks() {}

const string& Spellbooks::getSpellsName() const {
    return spellsName;
}

int Spellbooks::getManaConsumption() const {
    return manaConsumption;
}

int Spellbooks::getHitPoints() const {
    return hitPoints;
}