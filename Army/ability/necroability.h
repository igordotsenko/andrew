#ifndef NECROABILITY_H
#define NECROABILITY_H

#include "ability.h"
#include "../units/unit.h"
#include "../spells/spell.h"

class NecroAbility : public Ability {
    public:
        NecroAbility(Unit* currentUnit);
        virtual ~NecroAbility();

        virtual void castSpell(Unit* victim, Spell* spell);
};

#endif //NECROABILITY_H
