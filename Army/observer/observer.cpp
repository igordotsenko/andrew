#include "observer.h"

Observer::Observer() {}

Observer::~Observer() {}

void Observer::addObservable(Observable* observable) {
    observables.insert(observable);
    observable->addObserver(this);
}

void Observer::removeObservable(Observable* observable) {
    observables.erase(observable);
}

const set<Observable*>& Observer::getObservables() const {
    return observables;
}
