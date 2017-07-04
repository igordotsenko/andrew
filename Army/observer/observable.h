#ifndef OBSERVABLE_H
#define OBSERVABLE_H

#include <set>
#include "observer.h"

using namespace std;

class Observer;

class Observable {
    protected:
        set<Observer*> observers;

    public:
        Observable();
        virtual ~Observable() = 0;

        virtual const set<Observer*>& getObservers() const;

        virtual void addObserver(Observer* observer);
        virtual void removeObserver(Observer* observer);
};

#endif //OBSERVABLE_H