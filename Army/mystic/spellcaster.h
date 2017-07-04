#ifndef SPELLCASTER_H
#define SPELLCASTER_H

#include <iostream>
#include <map>
#include "../units/unit.h"
#include "../state/state.h"
#include "../ability/ability.h"
#include "../spells/spell.h"
#include "../spells/fireball.h"
#include "../spells/heal.h"

using namespace std;

class Spell;

class ManaIsOverException {};

class Spellcaster: public Unit {
    private:
        int MPLimits;
        int currentMP;
        map<string, Spell*> spellbook;
        
    protected:
        Spell* currentSpell;
        
        virtual void ensureManaIsNotOver();

    public:
        Spellcaster(const string& name, int healthPoint, int damage, int manaPoint);
        virtual ~Spellcaster();

        virtual void castSpell(Unit* victim);

        virtual void learnSpell(Spell* newSpell);

        virtual int getMPLimit() const;
        virtual int getCurrentMP() const;
        virtual Spell* getCurrentSpell() const;
        
        virtual void setMPLimit(int newMPLimit);
        virtual void setCurrentMP(int newCurrentMP);
        virtual void setCurrentSpell(const string& newCurrentSpell);
};

ostream& operator<<(std::ostream& out, const Spellcaster& spellcaster);

#endif //SPELLCASTER_H