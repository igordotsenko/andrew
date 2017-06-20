#include "fireball.h"

Fireball::Fireball() {
    setSpellsName("Fireball");
    setManaConsumption(8);
    setHitPoints(12);
    setSpellsType(battleSpell);
}

Fireball::~Fireball() {}