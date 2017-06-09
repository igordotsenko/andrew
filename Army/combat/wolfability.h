#ifndef WOLFABILITY_H
#define WOLFABILITY_H

#include "ability.h"
#include "../units/unit.h"
#include "../state/wolfstate.h"

class WolfAbility: public Ability {
    public:
        WolfAbility(Unit* currentUnit);
        virtual ~WolfAbility();
};

#endif //WOLFABILITY_H