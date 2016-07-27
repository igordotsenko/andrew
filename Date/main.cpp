#include <iostream>
#include "Date.h"

using namespace std;

int main(){
    int day, month, year;


    cout << "Enter the day: ";
    cin >> day;
    cout << "Enter the month: ";
    cin >> month;
    cout << "Enter the year: ";
    cin >> year;

    Date* date = new Date(day, month, year);

    cout << *date;

    delete (date);

    return 0;
}