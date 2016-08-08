#include <iostream>
#include "Identifiable.h"

using namespace std;

int main() {
    Identifiable* obj1 = new Identifiable;
    Identifiable* obj2 = new Identifiable;
    Identifiable* obj3 = new Identifiable;
    Identifiable* obj4 = new Identifiable;

    cout << "New object has id: " << obj1->getIdentifiable() << endl;
    cout << "New object has id: " << obj2->getIdentifiable() << endl;
    cout << "New object has id: " << obj3->getIdentifiable() << endl;
    cout << "New object has id: " << obj4->getIdentifiable() << endl;

    delete obj1;
    delete obj2;
    delete obj3;
    delete obj4;

    return 0;
}