#ifndef BERSERK_H
#define BERSERK_H

#include <iostream>
#include "unit.h"
#include "../ability/berserkability.h"
#include "../state/humanstate.h"

class Berserk : public Unit {
    public:
        Berserk(const string& name, int healthPoint, int damage);
        virtual ~Berserk();

        virtual void takeMagicDamage(int damage);
};


#endif //BERSERK_H