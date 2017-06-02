#include "spellcaster.h"

using namespace std;

Spellcaster::Spellcaster(const string& name, int healthPoint, int damage, int manaPoint) : Unit(name, healthPoint, damage) {
    this->MPLimits = manaPoint;
    this->currentMP = manaPoint;
}

Spellcaster::~Spellcaster() {}

void Spellcaster::castSpell(Unit* victim) {
    ensureIsAlive();
    ensureIsNotSelfAttack(victim);
    ensureIsNotAlly(victim);
    ensureManaIsNotOver();
    
    /*
        this part in process...
    ability->castSpell(victim, currentSpell); 
    currentMP -= currentSpell->getManaConsumption();
    */
}


int Spellcaster::getMPLimit() const {
    return MPLimits;
}

int Spellcaster::getCurrentMP() const {
    return currentMP;
}

bool Spellcaster::isBattleMage() const {
    return battleMage;
}

void Spellcaster::setMPLimit(int newMPLimit) {
    MPLimits = newMPLimit;
}

void Spellcaster::setCurrentMP(int newCurrentMP) {
    MPLimits = newCurrentMP;
}

void Spellcaster::ensureManaIsNotOver() {
    if ( currentMP < currentSpell->getManaConsumption() ) {
        throw ManaIsOverException();
    }
}

ostream& operator<<(std::ostream& out, const Spellcaster& spellcaster) {
    out << spellcaster.getName() << "\nHP: " << spellcaster.getHPLimit() << "|" << spellcaster.getCurrentHP() << endl;
    if ( spellcaster.getHPLimit() == 0 ) {
        return out;
    }
    out << "MP: " << spellcaster.getMPLimit() << "|" << spellcaster.getCurrentMP() << endl;
    out << "DMG: " << spellcaster.getDamage() << endl;

    return out;
}