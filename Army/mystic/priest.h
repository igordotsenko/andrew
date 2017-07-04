#ifndef PRIEST_H
#define PRIEST_H

#include "spellcaster.h"
#include "../ability/priestability.h"
#include "../state/humanstate.h"

using namespace std;

class Priest : public Spellcaster {
    public:
        Priest(const string& name, int healthPoint, int damage, int manaPoint);
        ~Priest();
};

#endif //PRIEST_H