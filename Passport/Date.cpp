#include <iostream>
#include "Date.h"

using namespace std;

#define FEBRUARY 1
#define MONTH_AMOUNT 12

void Date::validate(int day, int month, int year) {
    int daysInMonth[MONTH_AMOUNT] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int indexMonth = month - 1;

    if ( year % 400 == 0 || year % 4 == 0 && year % 100 != 0 ) {
        daysInMonth[FEBRUARY] += 1;
    }

    if ( month < 1 || month > MONTH_AMOUNT ) {
        throw InvalidDateException("Invalid Month!");
    }

    if ( day > daysInMonth[indexMonth] || day < 1 ) {
        throw InvalidDateException("Invalid Day!");
    }

}

Date::Date(int day, int month, int year) {
    validate(day, month, year);

    this->day = day;
    this->month = month;
    this->year = year;
}

Date::~Date(){}

int Date::getDay() const {
    return this->day;
}

int Date::getMonth() const {
    return this->month;
}

int Date::getYear() const {
    return this->year;
}

ostream& operator<<(std::ostream& out, const Date& date) {
    out << "Birthday: " << endl;
    out << "\tDay:   " << date.getDay() << endl;
    out << "\tMonth: " << date.getMonth() << endl;
    out << "\tYear:  " << date.getYear() << endl;

    return out;
}