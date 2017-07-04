#include "necroability.h"

NecroAbility::NecroAbility(Unit* currentUnit) : Ability(currentUnit) {}

NecroAbility::~NecroAbility() {}

void NecroAbility::castSpell(Unit* victim, Spell* spell) {
    if ( spell->getSpellsType() == battleSpell ) {
        victim->takeMagicDamage(spell->getHitPoints());
        
        return;
    }
    if ( spell->getSpellsType() == healSpell ) {
        victim->heal(spell->getHitPoints()/2);

        return;
    }
}
