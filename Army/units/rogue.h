#ifndef ROGUE_H
#define ROGUE_H

#include <iostream>
#include "unit.h"
#include "../ability/rogueability.h"
#include "../state/humanstate.h"

class Rogue : public Unit {
    public:
        Rogue(const string& name, int healthPoint, int damage);
        virtual ~Rogue();
};


#endif //ROGUE_H