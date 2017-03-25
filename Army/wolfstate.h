#ifndef WOLFSTATE_H
#define WOLFSTATE_H

#include "state.h"

class Unit;

class WolfState : public State {
    public:
        WolfState(Unit* currentStateUnit);
        virtual ~WolfState();

        virtual void takeMagicDamage(int damage);
};

#endif // WOLFSTATE_H