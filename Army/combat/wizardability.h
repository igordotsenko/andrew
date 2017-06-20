#ifndef WIZARDABILITY_H
#define WIZARDABILITY_H

#include "ability.h"
#include "../units/unit.h"
#include "../spells/spell.h"

class WizardAbility : public Ability {
    public:
        WizardAbility(Unit* currentUnit);
        virtual ~WizardAbility();

        virtual void castSpell(Unit* victim, Spell* spell);
};

#endif //WIZARDABILITY_H