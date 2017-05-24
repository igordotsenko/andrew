#ifndef STATE_H
#define STATE_H

#include <iostream>
#include "../units/unit.h"

using namespace std;

class Unit;

class State {
    protected:
        Unit* currentStateUnit;
        string name;
        int healthPointsLimit;
        int currentHP;
        int damage;
        double healthPointMultiplier;
        double damageMultiplier;
    
    public:
        State(Unit* currentStateUnit);
        virtual ~State() = 0;
        
        virtual void takeMagicDamage(int damage);

        virtual int getHPLimit() const;
        virtual int getDamage() const;
};

#endif //STATE_H