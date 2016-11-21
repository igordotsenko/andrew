#include <iostream>
#include <string>
#include "Car.h"
#include "Point.h"

using namespace std;

int main(){
    // Car* car = new Car(60, 0.6, (0, 0), "Mercedes");
    Car* car = new Car();

    cout << "Current information..." << endl;
    cout << *car << endl;

    cout << "Refilling... " << endl;
    car->refill(60);
    cout << *car << endl;

    cout << "Car driving..." << endl;
    car->drive(Point(5,11));
    cout << *car << endl;

    delete(car);

    return 0;
}