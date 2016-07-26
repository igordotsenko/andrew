#include <iostream>
#include "Date.h"

using namespace std;

void Date::validate(int day, int month, int year) {
<<<<<<< HEAD
    int daysInMonth[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    if ( day > daysInMonth[month] || day < 1 ) {
=======
    int daysInMonth[13] = {1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    if ( day > daysInMonth[month] || day < daysInMonth[0]) {
>>>>>>> 85c55802f1bc04971292ff3b200b9794a41775d8
        throw InvalidDateException("Invalid Day!");
    }
    if ( month < 1 || month > 12 ) {
        throw InvalidDateException("Invalid Month!");
    }
<<<<<<< HEAD
    if ( year == 0 ) {
        throw InvalidDateException("Invalid Year!");
    }
    if ( month == 4 || month == 6 || month == 9 || month == 11 ) {
        if ( day > 30 ) {
            throw InvalidDateException("Invalid Month!");
        }
    }
    if ( month == 2 ) {
        if ( year % 400 == 0 || year % 4 == 0 && year % 100 != 0 ) {
            if ( day > 29 ) {
                throw InvalidDateException("Error, February has 29 days!");
            }
        } else if ( day > 28 ) {
=======
    if ( year < 0 ) {
        throw InvalidDateException("Invalid Year!");
    }
    if ( month == 2 || month == 4 || month == 6 || month == 9 || month == 11 ) {
        if ( day == 31) {
            throw InvalidDateException("Invalid Month!");
        }
    }
    if ( day == 30 && month == 2 ) {
        throw InvalidDateException("Invalid Month!");
    }
    if ( year % 4 != 0 ) {
        if  ( day == 29 && month == 2 ) {
>>>>>>> 85c55802f1bc04971292ff3b200b9794a41775d8
            throw InvalidDateException("Error, February has 28 days!");
        }
    }
}

<<<<<<< HEAD
Date::Date(int day, int month, int year) {
=======
Date::Date(int day, int month, int year){
>>>>>>> 85c55802f1bc04971292ff3b200b9794a41775d8
    this->day = day;
    this->month = month;
    this->year = year;

    validate(day, month, year);
}

Date::~Date(){}

<<<<<<< HEAD
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
=======
int Date::getDay() const{
    return this->day;
}

int Date::getMonth() const{
    return this->month;
}

int Date::getYear() const{
    return this->year;
}

ostream& operator<<(std::ostream& out, const Date& date){

>>>>>>> 85c55802f1bc04971292ff3b200b9794a41775d8
    out << "Date: " << endl;
    out << "\tDay:   " << date.getDay() << endl;
    out << "\tMonth: " << date.getMonth() << endl;
    out << "\tYear:  " << date.getYear() << endl;

    return out;
}