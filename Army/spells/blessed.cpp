#include "blessed.h"

Blessed::Blessed() {
    setSpellsName("Blessed");
    setManaConsumption(50);
    setHitPoints(0);
    setSpellsType(blessedSpell);
}

Blessed::~Blessed() {}