#include "healerability.h"

HealerAbility::HealerAbility(Unit* currentUnit) : Ability(currentUnit) {}

HealerAbility::~HealerAbility() {}

void HealerAbility::castSpell(Unit* victim, Spell* spell) {
    if ( spell->getSpellsType() == battleSpell ) {
        victim->takeMagicDamage(spell->getHitPoints()/2);
        
        return;
    }
    if ( spell->getSpellsType() == healSpell ) {
        victim->heal(spell->getHitPoints());

        return;
    }
}
