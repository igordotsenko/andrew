#include "spell.h"

Spell::Spell() {}

Spell::~Spell() {}

const string& Spell::getSpellsName() const {
    return spellsName;
}

int Spell::getManaConsumption() const {
    return manaConsumption;
}

int Spell::getHitPoints() const {
    return hitPoints;
}

int Spell::getSpellsType() const {
    return spellsType;
}

void Spell::setSpellsName(const string& newSpellsName) {
    spellsName = newSpellsName;
}

void Spell::setManaConsumption(int newManaConsumption) {
    manaConsumption = newManaConsumption;
}

void Spell::setHitPoints(int newHitPoints) {
    hitPoints = newHitPoints;
}

void Spell::setSpellsType(int newSpellsType) {
    spellsType = newSpellsType;
}