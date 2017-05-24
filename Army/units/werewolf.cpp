#include "werewolf.h"

using namespace std;

Werewolf::Werewolf(const string& name, int healthPoint, int damage) : Unit(name, healthPoint, damage) {
    normalState = new HumanState(this);
    wolfState = new WolfState(this);
}

Werewolf::~Werewolf() {}

void Werewolf::changeState() {
    State* currentState = getCurrentState();
    State* nextState = getNextState();
    State* tmp = currentState;

    setCurrentState(nextState);
    setNextState(tmp);
    currentState = nextState;

    int newHitPoints = (int)(getHealthMultiplier() * (double)currentState->getHPLimit());

    setHPLimit(nextState->getHPLimit());
    setCurrentHP(newHitPoints); 
    setDamage(nextState->getDamage()); 
}

void Werewolf::takeMagicDamage(int damage) {
    ensureIsAlive();
    getCurrentState() -> takeMagicDamage(damage);
}

State* Werewolf::setCurrentState(State* newCurrentState) {
    normalState = newCurrentState;
}

State* Werewolf::setNextState(State* newNextState) {
    wolfState = newNextState;
}

State* Werewolf::getCurrentState() const {
    return normalState;
}

State* Werewolf::getNextState() const {
    return wolfState;
}

double Werewolf::getHealthMultiplier() const {
    return (double)getCurrentHP() / (double)getHPLimit();
}
