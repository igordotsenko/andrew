#include <iostream>
#include "Unit.h"

using namespace std;

int main() {
	Unit barbarian("Barbarian", 200, 18);
	Unit knight("Knight", 180, 20);

	cout << barbarian << endl;
	cout << knight << endl;

	for ( int i = 0; i < 9; i++ ) {
		barbarian.attack(knight);
	}
	
	cout << barbarian << endl;
	cout << knight << endl;

	cout << "Healing..." << endl;

	knight.addHitPoints(19);

	cout << knight << endl;

	return 0;
}