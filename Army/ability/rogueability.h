#ifndef ROGUEABILITY_H
#define ROGUEABILITY_H

#include "ability.h"
#include "../units/unit.h"

class RogueAbility: public Ability {
    public:
        RogueAbility(Unit* currentUnit);
        virtual ~RogueAbility();

        virtual void attack(Unit* victim);
};

#endif //ROGUEABILITY_H