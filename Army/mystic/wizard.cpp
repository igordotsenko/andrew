#include "wizard.h"

using namespace std;

Wizard::Wizard(const string& name, int healthPoint, int damage, int manaPoint) : Spellcaster(name, healthPoint, damage, manaPoint) {
    ability = new WizardAbility(this);
    unitType = wizardType;
    this->battleMage = true;
}

Wizard::~Wizard() {}
