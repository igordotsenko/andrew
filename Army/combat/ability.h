#ifndef ABILITY_H
#define ABILITY_H

#include <iostream>
#include "../units/unit.h"
#include "../state/state.h"

class Unit;
class Spellbooks;

class Ability {
    private:
        double getHealthMultiplier() const;
    protected:
        int unitType;
        Unit* currentUnit;

    public:
        Ability(Unit* currentUnit);
        virtual ~Ability() = 0;

        virtual void attack(Unit* victim);
        virtual void counterAttack(Unit* victim);
        virtual void takeMagicDamage(int damage);
        virtual void castSpell(Unit* victim, Spellbooks* spell);
        virtual void changeState();
};

#endif //ABILITY_H