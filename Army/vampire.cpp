#include "vampire.h"

using namespace std;

Vampire::Vampire(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage) {}

Vampire::~Vampire() {}

void Vampire::attack(Unit* victim) {
    ensureIsAlive();
    victim->takeDamage(getDamage());

    vampirism(victim);
    cout << "\t" << getName() << " taked " << getDamage() << " damage and recovered " << victim->getDamage()*0.4 << " HP" << endl;

    victim->counterAttack(this);
}

void Vampire::counterAttack(Unit* victim) {
    ensureIsAlive();

    victim->takeDamage(getDamage()/2);
    vampirism(victim);

    cout << "\t" << getName() << " counterattacking with " << getDamage()/2 << " damage and recovered " << victim->getDamage()*0.4 << " HP\n" << endl;
}

void Vampire::vampirism(Unit* victim) {
    ensureIsAlive();

    int recoverHealthPoint = victim->getDamage() * 0.4;

    victim->takeDamage(victim->getDamage()*0.4);
    if ( getHealthtPointsLimit() - getCurrentHealthPoint() < recoverHealthPoint ) {
        currentHealthPoint = getHealthtPointsLimit();

        return;
    }

    currentHealthPoint += recoverHealthPoint;
}
