#include "observable.h"

Observable::Observable() {}

Observable::~Observable() {}

void Observable::addObserver(Observer* observer) {
    observers.insert(observer);
}

void Observable::removeObserver(Observer* observer) {
    observers.erase(observer);
}

const set<Observer*>& Observable::getObservers() const {
    return observers;
}