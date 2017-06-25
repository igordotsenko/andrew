#include "priestability.h"

PriestAbility::PriestAbility(Unit* currentUnit) : Ability(currentUnit) {}

PriestAbility::~PriestAbility() {}

void PriestAbility::castSpell(Unit* victim, Spell* spell) {
    if ( spell->getSpellsType() == battleSpell ) {
        victim->takeMagicDamage(spell->getHitPoints()/2);
        
        return;
    }
    if ( spell->getSpellsType() == healSpell ) {
        victim->heal(spell->getHitPoints()*1.5);

        return;
    }
}
