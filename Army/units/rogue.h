#ifndef ROGUE_H
#define ROGUE_H

#include <iostream>
#include "unit.h"
#include "../combat/rogueability.h"

class Rogue : public Unit {
    public:
        Rogue(const string& name, int healthPoint, int damage);
        virtual ~Rogue();

        virtual void attack(Unit* victim);
};


#endif //ROGUE_H