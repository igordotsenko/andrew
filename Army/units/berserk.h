#ifndef BERSERK_H
#define BERSERK_H

#include <iostream>
#include "unit.h"
#include "../combat/berserkability.h"

class Berserk : public Unit {
    public:
        Berserk(const string& name, int healthPoint, int damage);
        virtual ~Berserk();

        virtual void takeMagicDamage(int damage);
};


#endif //BERSERK_H