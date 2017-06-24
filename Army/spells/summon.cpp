#include "summon.h"

Summon::Summon() {
    setSpellsName("Summon");
    setManaConsumption(15);
    setHitPoints(0);
    setSpellsType(summonSpell);
}

Summon::~Summon() {}