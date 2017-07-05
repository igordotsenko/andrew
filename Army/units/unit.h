#ifndef UNIT_H
#define UNIT_H

#include <iostream>
#include "../ability/ability.h"
#include "../observer/observer.h"
#include "../observer/observable.h"

using namespace std;

class Ability;
class State;

class UnitIsDeadException {};
class IsSelfAttackException {};
class IsVictimException {};
class IsFriendlyAttackException {};

enum UnitType {
    soldier,
    rogue,
    berserk,
    vampire,
    werewolf,
    wizard,
    healer,
    priest,
    warlock,
    necromancer
};

class Unit: public Observer, public Observable {
    private:
        string name;
        int healthPointLimit;
        int currentHP;
        int damage;
        int unitType;
        bool isDead;
        State* normalState;
        State* alternativeState;
        Ability* ability;
        
    protected:
        virtual void ensureIsNotSelfAttack(Unit* victim);
        virtual void ensureIsAlive();

    public:
        Unit(const string& name, int healthPoint, int damage);
        virtual ~Unit() = 0;

        virtual void attack(Unit* victim);
        virtual void takeDamage(int damage);
        virtual void takeMagicDamage(int damage);
        virtual void counterAttack(Unit* victim);
        virtual void changeState();

        virtual void setHPLimit(int newHPLimit);
        virtual void setCurrentHP(int newCurrentHP);
        virtual void setDamage(int damage);
        virtual void setIsDead();

        virtual void setAbility(Ability* newAbility);

        virtual void setCurrentState(State* newCurrentState);
        virtual void setNextState(State* newNextState);

        virtual void setUnitType(int newUnitType);

        virtual const string& getName() const;
        virtual int getHPLimit() const;
        virtual int getCurrentHP() const;
        virtual int getDamage() const;
        virtual bool getIsDead() const;

        virtual Ability* getAbility() const;

        virtual State* getCurrentState() const;
        virtual State* getNextState() const;

        virtual const int getUnitType() const;
        
        virtual const set<Observer*>& getObservers() const;
        virtual const set<Observable*>& getObservables() const;

        virtual void notifyObservers();
        virtual void notifyObservable();

        virtual void heal(int healthPoint);
};

ostream& operator<<(ostream& out, const Unit& unit);

#endif //UNIT_H