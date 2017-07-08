#ifndef PRIESTABILITY_H
#define PRIESTABILITY_H

#include "ability.h"
#include "../units/unit.h"
#include "../state/angelstate.h"
#include "../spells/spell.h"

class IsInfectedUnitsException {};

class PriestAbility : public Ability {
    private:
        void ensureIsNotInfected(Unit* victim);

    public:
        PriestAbility(Unit* currentUnit);
        virtual ~PriestAbility();

        virtual void castSpell(Unit* victim, Spell* spell);
};

#endif //PRIESTABILITY_H