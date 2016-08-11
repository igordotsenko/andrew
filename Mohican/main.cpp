#include <iostream>
#include "Mohican.h"

using namespace std;

int main() {
    Mohican* mohican1 = new Mohican("Stepan");
    Mohican* mohican2 = new Mohican("Pedro");
    Mohican* mohican3 = new Mohican("Arturo");
    Mohican* mohican4 = new Mohican("Don Huan Stebal'");
    Mohican* mohican5 = new Mohican("Leha");

    cout << Mohican::getLastMohican().getName() << endl;

    delete mohican5;
    delete mohican4;

    cout << Mohican::getLastMohican().getName() << endl;

    Mohican* mohican6 = new Mohican("Lena");

    cout << Mohican::getLastMohican().getName() << endl;

    return 0;
}