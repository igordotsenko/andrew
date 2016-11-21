#include <iostream>
#include "Arithmetic.h"

using namespace std;

int main() {
    int index;
    int first, last, step;

    cout << "Set first value: ";
    cin >> first;
    cout << "Set last number of last member: ";
    cin >> last;
    cout << "Set common difference: ";
    cin >> step;
    Arithmetic seq(first, last, step);

    for ( ; !seq.over(); seq++ ) {
        cout << seq.getIndex() << " - " << *seq << endl;
    }

    cout << endl;
    cout << "Current value and last member: " << *seq << " - " << seq.getIndex() << endl;

    seq.resetToLast();

    seq.prev();
    seq.prev();
    seq.prev();
    seq.prev();

    cout << "Current value after prev(): " << *seq << " - " << seq.getIndex() << endl;

    seq.next();
    seq.next();

    cout << "Current value after next(): " << *seq << " - " << seq.getIndex() << endl;

    seq.resetToFirst();
    cout << "Current value after reset to begin: " << *seq << " - " << seq.getIndex() << endl;

    seq.resetToLast();

    cout << "Current value after reset to last: " << *seq << " - " << seq.getIndex() << endl;

    cout << endl;

    for ( ; !seq.over(); seq-- ) {
        cout << seq.getIndex() << " - " << *seq << endl;
    }
    cout << endl;

    cout << "Last: " << seq.lastMember() << endl;
    cout << "Summary: " << seq.sum() << endl;

    cout << "Enter Index: ";
    cin >> index;
    cout << "Value " << seq.getValueAtIndex(index) << " at index - " << index << endl;

    return 0;
}