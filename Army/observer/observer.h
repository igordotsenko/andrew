#ifndef OBSERVER_H
#define OBSERVER_H

#include <iostream>
#include <set>
#include "observable.h"

using namespace std;

class Observable;

class Observer {
    protected:
        set<Observable*> observables;

    public:
        Observer();
        virtual ~Observer() = 0;

        virtual const set<Observable*>& getObservables() const;

        virtual void addObservable(Observable* observable);
        virtual void removeObservable(Observable* observable);
};

#endif //OBSERVER_H