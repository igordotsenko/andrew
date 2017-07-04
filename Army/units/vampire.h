#ifndef VAMPIRE_H
#define VAMPIRE_H

#include "unit.h"
#include "../ability/vampireability.h"
#include "../state/humanstate.h"

class Vampire: public Unit {
    public:
        Vampire(const string& name, int healthPoint, int damage);
        virtual ~Vampire();
};


#endif //VAMPIRE_H