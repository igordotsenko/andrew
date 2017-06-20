#include "spellcaster.h"

using namespace std;

Spellcaster::Spellcaster(const string& name, int healthPoint, int damage, int manaPoint) : Unit(name, healthPoint, damage) {
    this->MPLimits = manaPoint;
    this->currentMP = manaPoint;
    learnSpell(new Fireball);
    learnSpell(new Heal);
}

Spellcaster::~Spellcaster() {
    cout << spellbook.size() << endl;
    spellbook.clear();
    cout << spellbook.size() << endl;
}

void Spellcaster::castSpell(Unit* victim) {
    ensureIsAlive();
    ensureIsNotSelfAttack(victim);
    ensureManaIsNotOver();
    
    getAbility()->castSpell(victim, currentSpell); 
    currentMP -= getCurrentSpell()->getManaConsumption();
}

void Spellcaster::learnSpell(Spell* newSpell) {
    spellbook[newSpell->getSpellsName()] = newSpell;
}

Spell* Spellcaster::getCurrentSpell() const {
    return currentSpell;
}

int Spellcaster::getMPLimit() const {
    return MPLimits;
}

int Spellcaster::getCurrentMP() const {
    return currentMP;
}

void Spellcaster::setMPLimit(int newMPLimit) {
    MPLimits = newMPLimit;
}

void Spellcaster::setCurrentMP(int newCurrentMP) {
    currentMP = newCurrentMP;
}

void Spellcaster::setCurrentSpell(const string& newCurrentSpell) {
    currentSpell = spellbook[newCurrentSpell];
}

void Spellcaster::ensureManaIsNotOver() {
    if ( getCurrentMP() < getCurrentSpell()->getManaConsumption() ) {
        throw ManaIsOverException();
    }
}

ostream& operator<<(ostream& out, const Spellcaster& spellcaster) {
    out << spellcaster.getName() << "\nHP: " << spellcaster.getHPLimit() << "|" << spellcaster.getCurrentHP() << endl;
    if ( spellcaster.getHPLimit() == 0 ) {
        return out;
    }
    out << "MP: " << spellcaster.getMPLimit() << "|" << spellcaster.getCurrentMP() << endl;
    out << "DMG: " << spellcaster.getDamage() << endl;

    return out;
}