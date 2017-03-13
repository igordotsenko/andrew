#ifndef UNIT_H
#define UNIT_H

#include <iostream>

using namespace std;

class UnitIsDeadException {};

class Unit {
    protected:
        string name;
        int healthPointLimit;
        int currentHealthPoint;
        int damage;
        virtual void ensureIsAlive();

    public:
        Unit(const string& name, int healthPoint, int damage);
        ~Unit();

        virtual void attack(Unit* victim);
        virtual void takeDamage(int damage);
        // virtual void takeMagicDamage(int damage);
        virtual void counterAttack(Unit* victim);

        virtual const string& getName() const;
        virtual int getDamage() const;
        virtual int getHealthtPointsLimit() const;
        virtual int getCurrentHealthPoint() const;
};

ostream& operator<<(ostream& out, const Unit& unit);

#endif //UNIT_H