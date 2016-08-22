#include <iostream>
#include "Passport.h"
#include "Date.h"

using namespace std;

int main() {

    Passport people("Steve", "Rogers", 19, 05, 1913);
    cout << people << endl;

    Passport::setSeries("BB");
    Passport people1("Backie1", "Barns1", 13, 12, 1993);
    cout << people1 << endl;

    Passport people2("Backie2", "Barns2", 13, 12, 1993);
    cout << people2 << endl;

    // Passport::setSeries("AB", 000002);
    // Passport people3("Backie3", "Barns3", 13, 12, 1993);
    // cout << people3 << endl;

    Passport::setSeries("CC", 000001);
    Passport people4("Backie4", "Barns4", 13, 12, 1993);
    cout << people4 << endl;

    Passport::setSeries("ZZ", 999999);
    Passport people5("Backie5", "Barns5", 13, 12, 1993);
    cout << people5 << endl;

    Passport people6("Backie6", "Barns6", 13, 12, 1993);
    cout << people6 << endl;

    return 0;
}