#include <iostream>
#include "Passport.h"
#include "Date.h"

using namespace std;

int main() {

    Passport::setSeries("MZ", 999999);
    Passport people1("Andrew", "Solod", 13, 12, 1993);
    cout << people1 << endl;
    Passport people2("Marry", "Reed", 9, 05, 1995);
    cout << people2 << endl;

    return 0;
}