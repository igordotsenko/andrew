#ifndef WARLOCK_H
#define WARLOCK_H

#include "spellcaster.h"
#include "../spells/summon.h"
#include "../combat/warlockability.h"
#include "../state/state.h"

class IsNotSummonSpellsException{};
class DemonIsAlreadySummonedException{};

class Warlock : public Spellcaster {
    private:
        Demon* demon;
        void ensureIsSummonSpell(Spell* spell);
        void ensureIsNotSummonedDemon();

    public:
        Warlock(const string& name, int healthPoint, int damage, int manaPoint);
        virtual ~Warlock();

        Demon* summonDemon();
        Demon* demonsReturn();
        Demon* getDemon() const;
};

#endif //WARLOCK_H
