#include <iostream>
#include <cmath>

#include "Vector.h"

using namespace std;

int main() {
    int x, y;
    Vector result;

    cout << "Point A: ";
    cin >> x >> y;
    Vector a(x, y);
    cout << "Point B: ";
    cin >> x >> y;
    Vector b(x, y);

    if ( a == b ) {
       cout << a << " is equal to " << b << endl;
    } else {
       cout << a << " is not equal to " << b << endl;
    }

    cout << "Length: " << a.len(b) << endl;
    cout << "Summary:" << a + b << endl;
    cout << "Different: " << a - b << endl;



    return 0;
}