#ifndef WEREWOLF_H
#define WEREWOLF_H

#include <iostream>
#include "unit.h"
#include "../state/state.h"
#include "../state/wolfstate.h"
#include "../state/humanstate.h"
#include "../ability/wolfability.h"

class Werewolf: public Unit {
    public:
        Werewolf(const string& name, int healthPoint, int damage);
        virtual ~Werewolf();
};

#endif //WEREWOLF_H