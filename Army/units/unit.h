#ifndef UNIT_H
#define UNIT_H

#include <iostream>
#include "../combat/ability.h"

using namespace std;

class Ability;
class State;
class UnitIsDeadException {};
class IsSelfAttackException {};

enum UnitType {
    soldierType,
    rogueType,
    berserkType,
    vampireType,
    werewolfType
};

class Unit {
    private:
        string name;
        int healthPointLimit;
        int currentHP;
        int damage;
        
    protected:
        int unitType;
        State* normalState;
        State* wolfState;
        Ability* ability;

        virtual void ensureIsAlive();
        virtual void ensureIsNotSelfAttack(Unit* victim);

    public:
        Unit(const string& name, int healthPoint, int damage);
        virtual ~Unit();

        virtual void attack(Unit* victim);
        virtual void takeDamage(int damage);
        virtual void takeMagicDamage(int damage);
        virtual void counterAttack(Unit* victim);
        virtual void changeState();

        virtual void setHPLimit(int newHPLimit);
        virtual void setCurrentHP(int newCurrentHP);
        virtual void setDamage(int damage);

        virtual void* setCurrentState(State* newCurrentState);
        virtual void* setNextState(State* newNextState);

        virtual const string& getName() const;
        virtual int getHPLimit() const;
        virtual int getCurrentHP() const;
        virtual int getDamage() const;

        virtual State* getCurrentState() const;
        virtual State* getNextState() const;

        virtual const int getUnitType() const;

        virtual void heal(int healthPoint);
};

ostream& operator<<(ostream& out, const Unit& unit);

#endif //UNIT_H