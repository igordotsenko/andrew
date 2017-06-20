#ifndef DEFAULTABILITY_H
#define DEFAULTABILITY_H

#include "ability.h"
#include "../units/unit.h"

class DefaultAbility: public Ability {
    public:
        DefaultAbility(Unit* currentUnit);
        virtual ~DefaultAbility();

};

#endif //DEFAULTABILITY_H