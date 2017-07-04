#ifndef PRIESTABILITY_H
#define PRIESTABILITY_H

#include "ability.h"
#include "../units/unit.h"
#include "../spells/spell.h"

class PriestAbility : public Ability {
    public:
        PriestAbility(Unit* currentUnit);
        virtual ~PriestAbility();

        virtual void castSpell(Unit* victim, Spell* spell);
};

#endif //PRIESTABILITY_H