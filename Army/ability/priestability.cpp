#include "priestability.h"

PriestAbility::PriestAbility(Unit* currentUnit) : Ability(currentUnit) {}

PriestAbility::~PriestAbility() {}

void PriestAbility::castSpell(Unit* victim, Spell* spell) {
    if ( spell->getSpellsType() == battleSpell ) {
        if ( victim->getIsDead() ) {
        victim->takeMagicDamage(spell->getHitPoints()*2);

        return;
        }
        victim->takeMagicDamage(spell->getHitPoints()/2);

        return;
    }
    if ( spell->getSpellsType() == healSpell ) {
        victim->heal(spell->getHitPoints()*1.5);

        return;
    }
    if ( spell->getSpellsType() == blessedSpell ) {
        ensureIsNotInfected(victim);
        victim->setNextState(new AngelState(victim));

        return;
    }
}

void PriestAbility::ensureIsNotInfected(Unit* victim) {
    if ( victim->getUnitType() == werewolf || victim->getUnitType() == vampire ) {
        throw IsInfectedUnitsException();
    }

}