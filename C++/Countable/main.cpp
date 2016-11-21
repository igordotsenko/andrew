#include <iostream>
#include "Countable.h"
using namespace std;

int main() {
    Countable* object1 = new Countable();
    Countable* object2 = new Countable();
    Countable* object3 = new Countable();

    cout << "Amount object: " << Countable::getTotalCount() << endl;

    delete object3;

    cout << "Amount object: " << Countable::getTotalCount() << endl;

    delete object1;
    delete object2;

    return 0;
}