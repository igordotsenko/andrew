#include <iostream>
#include "Arithmetic.h"

using namespace std;

int main() {
    Arithmetic seq(1, 20, 2);

    for ( ; !seq.over(); seq++ ) {
        cout << *seq << " ";
    }
    cout << endl;

    seq.resetToLast();

    for ( ; !seq.over(); seq-- ) {
        cout << *seq << " " ;
    }
    cout << endl;

    cout << "Enter Index: ";
    cin >> index;
    cout << seq.getValueAtIndex(index) << endl;

    return 0;
}