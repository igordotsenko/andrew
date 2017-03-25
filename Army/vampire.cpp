#include "vampire.h"

using namespace std;

Vampire::Vampire(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage) {}

Vampire::~Vampire() {}

void Vampire::attack(Unit* victim) {
    ensureIsAlive();

    victim->takeDamage(getDamage());

    vampirism(victim);
    cout << "\t" << getName() << " hit for " << getDamage() << " damage and recovered " << victim->getCurrentHP() / 10 << " HP\n";

    victim->counterAttack(this);
}

void Vampire::counterAttack(Unit* victim) {
    ensureIsAlive();

    victim->takeDamage(getDamage() / 2);
    vampirism(victim);

    cout << "\t" << getName() << " is counter attacking for " << getDamage()/2 << " damage and recovering " << getCurrentHP() / 10 << " HP\n" << endl;
}

void Vampire::vampirism(Unit* victim) {
    ensureIsAlive();

    int recoverHP = victim->getCurrentHP() / 10;

    if ( getHPLimit() - getCurrentHP() < recoverHP ) {
        setCurrentHP(getHPLimit());

        return;
    }

    setCurrentHP(getCurrentHP() + recoverHP);
}
