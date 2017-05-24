#ifndef VAMPIRE_H
#define VAMPIRE_H

#include <iostream>
#include "unit.h"

class Vampire: public Unit {
    private:
        void vampirism(Unit* victim);

    public:
        Vampire(const string& name, int healthPoint, int damage);
        virtual ~Vampire();

        virtual void attack(Unit* victim);
        virtual void counterAttack(Unit* victim);
};


#endif //VAMPIRE_H