#include <iostream>
#include "Gun.h"

using namespace std;

int main() {
    Gun beretta("Beretta", 8);
    int shot;
    cout << beretta << endl;
    
    cout << "How many shots: ";
    cin >> shot;

    cout << "Reloaded..." << endl;
    beretta.reload();
    cout << "Preparing..." << endl;
    beretta.prepare();
    
    cout << beretta << endl;
    
    for ( int i = 0; i < shot; i++ ) {
        beretta.shoot();
    }

    cout << endl;
    
    // cout << gun << endl;
    cout << beretta << endl;
    
    return 0;
}