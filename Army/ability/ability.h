#ifndef ABILITY_H
#define ABILITY_H

#include <iostream>
#include "../units/unit.h"
#include "../state/state.h"

class Unit;
class Spell;

class Ability {
    private:
        int unitType;
        double getHealthMultiplier() const;
        
    protected:
        Unit* currentUnit;

    public:
        Ability(Unit* currentUnit);
        virtual ~Ability() = 0;

        virtual void attack(Unit* victim);
        virtual void counterAttack(Unit* victim);
        virtual void takeDamage(int damage);
        virtual void takeMagicDamage(int damage);
        virtual void castSpell(Unit* victim, Spell* spell);
        virtual void changeState();
};

#endif //ABILITY_H