#ifndef HEALERABILITY_H
#define HEALERABILITY_H

#include "ability.h"
#include "../units/unit.h"
#include "../spells/spell.h"

class HealerAbility : public Ability {
    public:
        HealerAbility(Unit* currentUnit);
        virtual ~HealerAbility();

        virtual void castSpell(Unit* victim, Spell* spell);
};

#endif //HEALERABILITY_H