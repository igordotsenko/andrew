#ifndef SOLDIER_H
#define SOLDIER_H

#include <iostream>
#include "unit.h"
#include "../combat/defaultability.h"

class Soldier: public Unit {
    public:
        Soldier(const string& name, int healthPoint, int damage);
        virtual ~Soldier();
};


#endif //SOLDIER_H