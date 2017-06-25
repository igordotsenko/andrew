#ifndef BERSERKABILITY_H
#define BERSERKABILITY_H

#include "ability.h"
#include "../units/unit.h"

class BerserkAbility: public Ability {
    public:
        BerserkAbility(Unit* currentUnit);
        virtual ~BerserkAbility();

        virtual void takeMagicDamage(int damage);
};

#endif //BERSERKABILITY_H