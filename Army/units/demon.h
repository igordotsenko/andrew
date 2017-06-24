#ifndef DEMON_H
#define DEMON_H

#include "unit.h"
#include "soldier.h"
#include "../state/humanstate.h"

class Warlock;

class MasterAttackedException {};

class Demon : public Soldier {
    private:
        Warlock* master;
        void ensureIsNotMaster(Unit* target);

    public:
        Demon(Warlock* master);
        virtual ~Demon();

        virtual void attack(Unit* victim);
        Warlock* getMaster() const;
};

#endif //DEMON_H