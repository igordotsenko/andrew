#ifndef VAMPIRE_H
#define VAMPIRE_H

#include <iostream>
#include "unit.h"
#include "../combat/vampireability.h"

class Vampire: public Unit {
    public:
        Vampire(const string& name, int healthPoint, int damage);
        virtual ~Vampire();
};


#endif //VAMPIRE_H