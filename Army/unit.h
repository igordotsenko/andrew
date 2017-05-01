#ifndef UNIT_H
#define UNIT_H

#include <iostream>

using namespace std;

class UnitIsDeadException {};

class Unit {
    private:
        string name;
        int healthPointLimit;
        int currentHP;
        int damage;
        
    protected:
        virtual void ensureIsAlive();

    public:
        Unit(const string& name, int healthPoint, int damage);
        virtual ~Unit();

        virtual void attack(Unit* victim);
        virtual void takeDamage(int damage);
        virtual void takeMagicDamage(int damage);
        virtual void counterAttack(Unit* victim);

        virtual void setHPLimit(int newHPLimit);
        virtual void setCurrentHP(int newCurrentHP);
        virtual void setDamage(int damage);

        virtual const string& getName() const;
        virtual int getHPLimit() const;
        virtual int getCurrentHP() const;
        virtual int getDamage() const;

        virtual void heal(int healthPoint);
};

ostream& operator<<(ostream& out, const Unit& unit);

#endif //UNIT_H