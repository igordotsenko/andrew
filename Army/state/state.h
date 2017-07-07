#ifndef STATE_H
#define STATE_H

#include <iostream>
#include "../units/unit.h"
#include "../observer/observer.h"
#include "../observer/observable.h"

using namespace std;

class Unit;
class Observer;
class Observable;

class IsNotVampireTypeException {};

class State {
    private:
        void ensureIsVampireType();

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
        virtual void vampirism(Unit* victim);

        virtual int getCurrentHP() const;
        virtual int getHPLimit() const;
        virtual int getDamage() const;
};

#endif //STATE_H