#include <iostream>
#include "Arithmetic.h"

using namespace std;

int main() {
    int first, last, diff;
    int index;
    cout << "Set first number: ";
    cin >> first;
    cout << "Set last number: ";
    cin >> last;
    cout << "Set common difference: ";
    cin >> diff;

    Arithmetic seq(first, last, diff);

    for ( ; !seq.over(); seq++ ) {
        cout << *seq << " ";
    }
    cout << endl;

    cout << "Enter index: ";
    cin >> index;

    cout << "Last member: " << seq.lastMember() << endl;
    cout << "Summary: " << seq.summary() << endl;

    cout << "Jump to index " << index << ": " << seq.getValueAtIndex(index) << endl;

    return 0;
}