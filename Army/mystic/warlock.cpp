#include "warlock.h"

Warlock::Warlock(const string& name, int healthPoint, int damage, int manaPoint) : Spellcaster(name, healthPoint, damage, manaPoint) {
    setAbility(new WarlockAbility(this));
    setUnitType(warlock);
    learnSpell(new Summon);
    setCurrentSpell("Summon");
    setCurrentState(new HumanState(this));
    setNextState(new HumanState(this));
    demon = NULL;
}

Warlock::~Warlock() {
    demonsReturn();
}

Demon* Warlock::summonDemon() {
    ensureIsSummonSpell(getCurrentSpell());
    ensureIsNotSummonedDemon();
    ensureManaIsNotOver();

    demon = new Demon(this);
    setCurrentMP(getCurrentMP() - getCurrentSpell()->getManaConsumption());

    return demon;
}

Demon* Warlock::demonsReturn() {
    delete demon;
}

Demon* Warlock::getDemon() const {
    return demon;
}

void Warlock::ensureIsSummonSpell(Spell* spell) {
    if ( getCurrentSpell()->getSpellsType() != summonSpell ) {
        throw IsNotSummonSpellsException();
    }
}

void Warlock::ensureIsNotSummonedDemon() {
    if ( demon != NULL ) {
        throw DemonIsAlreadySummonedException();
    }
}