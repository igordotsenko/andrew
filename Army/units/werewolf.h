#ifndef WEREWOLF_H
#define WEREWOLF_H

#include <iostream>
#include "unit.h"
#include "state.h"
#include "wolfstate.h"
#include "humanstate.h"

class Werewolf: public Unit {
    protected:
        State* normalState;
        State* wolfState;

    public:
        Werewolf(const string& name, int healthPoint, int damage);
        virtual ~Werewolf();

        virtual void changeState();
        virtual void takeMagicDamage(int damage);

        State* setCurrentState(State* newCurrentState);
        State* setNextState(State* newNextState);

        State* getCurrentState() const;
        State* getNextState() const;
        double getHealthMultiplier() const;

};

#endif //WEREWOLF_H