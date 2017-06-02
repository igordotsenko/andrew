#ifndef WIZARDABILITY_H
#define WIZARDABILITY_H

#include "ability.h"
#include "../units/unit.h"

class WizardAbility : public Ability {
    public:
        WizardAbility(Unit* currentUnit);
        virtual ~WizardAbility();

        virtual void castSpell(Unit* victim, Spellbooks* spell);
};

#endif //WIZARDABILITY_H