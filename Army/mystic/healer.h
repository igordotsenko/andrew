#ifndef HEALER_H
#define HEALER_H

#include "spellcaster.h"
#include "../ability/healerability.h"
#include "../state/humanstate.h"

using namespace std;

class Healer : public Spellcaster {
    public:
        Healer(const string& name, int healthPoint, int damage, int manaPoint);
        ~Healer();
};

#endif //HEALER_H