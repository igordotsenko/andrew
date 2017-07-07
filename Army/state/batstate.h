#ifndef BATSTATE_H
#define BATSTATE_H

#include "state.h"

class Unit;

class BatState : public State {
    public:
        BatState(Unit* currentStateUnit);
        virtual ~BatState();

        virtual void vampirism(Unit* victim);
};

#endif // BATSTATE_H