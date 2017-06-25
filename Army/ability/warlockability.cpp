#include "warlockability.h"

WarlockAbility::WarlockAbility(Unit* currentUnit) : Ability(currentUnit) {}

WarlockAbility::~WarlockAbility() {}

void WarlockAbility::castSpell(Unit* victim, Spell* spell) {
    if ( spell->getSpellsType() == battleSpell ) {
        victim->takeMagicDamage(spell->getHitPoints()/2);
        
        return;
    }
    if ( spell->getSpellsType() == healSpell ) {
        victim->heal(spell->getHitPoints()/2);

        return;
    }
}
