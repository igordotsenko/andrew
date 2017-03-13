#include "berserk.h"

using namespace std;

Berserk::Berserk(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage) {}

Berserk::~Berserk() {}

void Berserk::takeMagicDamage(int damage) {
    
}