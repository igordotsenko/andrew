#include <iostream>
#include "Complex.h"

using namespace std;

int main() {
    int x, y;
    Complex result;
    Complex decrement;

    cout << "real:      ";
    cin >> x >> y;
    Complex real(x, y);
    cout << "imaginary: ";
    cin >> x >> y;
    Complex imaginary(x, y);

    if ( real == imaginary ) {
       cout << real << " is equal to " << imaginary << endl;
    } else {
       cout << real << " is not equal to " << imaginary << endl;
    }

    result = real.operator+(imaginary);
    cout << "summary: " << result << endl;

    result = real.operator-(imaginary);
    cout << "difference: " << result << endl;

    
    cout << "Decrement: " << result << endl;

    return 0;
}