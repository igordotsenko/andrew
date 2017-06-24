#ifndef WARLOCKABILITY_H
#define WARLOCKABILITY_H

#include "ability.h"
#include "../units/unit.h"
#include "../spells/spell.h"
#include "../units/demon.h"

class WarlockAbility : public Ability {
    private:
        Demon* demon;

    public:
        WarlockAbility(Unit* currentUnit);
        virtual ~WarlockAbility();

        virtual void castSpell(Unit* victim, Spell* spell);
};

#endif //WARLOCKABILITY_H
