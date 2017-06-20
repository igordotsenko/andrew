#include "wizardability.h"

WizardAbility::WizardAbility(Unit* currentUnit) : Ability(currentUnit) {}

WizardAbility::~WizardAbility() {}

void WizardAbility::castSpell(Unit* victim, Spell* spell) {
    if ( spell->getSpellsType() == battleSpell ) {
        victim->takeMagicDamage(spell->getHitPoints());
        
        return;
    }
    if ( spell->getSpellsType() == healSpell ) {
        victim->heal(spell->getHitPoints()/2);

        return;
    }
}