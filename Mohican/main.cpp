#include <iostream>
#include "Mohican.h"

using namespace std;

int main() {
    Mohican* mohican1 = new Mohican("mohican_1");
    Mohican* mohican2 = new Mohican("mohican_2");
    Mohican* mohican3 = new Mohican("mohican_3");
    Mohican* mohican4 = new Mohican("mohican_4");
    Mohican* mohican5 = new Mohican("mohican_5");

    cout << "Last mohican is: " + Mohican::getLastMohican().getName() << endl;

    delete mohican5;
    delete mohican4;
    delete mohican3;
    delete mohican2;
    delete mohican1;

    cout << "Last mohican is: " + Mohican::getLastMohican().getName() << endl;

    return 0;
}