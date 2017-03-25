#ifndef HUMAN_STATE_H
#define HUMAN_STATE_H

#include "state.h"

class Unit;

class HumanState : public State {
    public:
        HumanState(Unit* currentStateUnit);
        virtual ~HumanState();
};

#endif //HUMAN_STATE_H