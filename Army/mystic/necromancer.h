#ifndef NECROMANCER_H
#define NECROMANCER_H

#include "spellcaster.h"
#include "../ability/necroability.h"
#include "../state/humanstate.h"

using namespace std;

class Necromancer: public Spellcaster {
    public:
        Necromancer(const string& name, int healthPoint, int damage, int manaPoint);
        virtual ~Necromancer();

        virtual void attack(Unit* victim);
        virtual void castSpell(Unit* victim);
};

#endif //NECROMANCER_H