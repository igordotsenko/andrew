#include <iostream>
#include <iomanip>
#include <cctype>
#include "Passport.h"

#define SIZE 3
#define MAX_SERIAL 999999
#define FIRST_LETTER 0
#define SECOND_LETTER 1

using namespace std;

char Passport::newSeries[] = {'A', 'A', 0};
int Passport::newSerialNumber = 1;

void Passport::validateSeries(string series) {
    int last = SIZE - 1;

    if ( series.size() > last ) {
        throw InvalidSerialException();
    }
    for ( int i = 0; i < last; i++ ) {
        if ( series[i] < 'A' || series[i] > 'Z' ) {
            throw InvalidSerialException();
        }
    }
    if ( newSeries > series ) {
        throw InvalidSerialException();
    }
}

void Passport::validateSerialNumber(int serialNumber) {
    if ( serialNumber < 1 || serialNumber > MAX_SERIAL ) {
        throw InvalidSerialNumberException();
    }
}

void Passport::validate(string series, int serialNumber) {
    Passport::validateSeries(series);
    Passport::validateSerialNumber(serialNumber);
    if ( newSeries == series && newSerialNumber > serialNumber ) {
        throw InvalidSerialException();
    }
}

Passport::Passport(string name, string surname, int bDay, int bMonth, int bYear) {
    this->name = name;
    this->surname = surname;
    this->birthDate = Date(bDay, bMonth, bYear);

    this->seriesGeneration();
}

Passport::~Passport() {}

void Passport::seriesGeneration() {
    for ( int i = 0; i < SIZE; i++ ) {
        this->series[i] = newSeries[i];
    }
    this->serialNumber = newSerialNumber;

    if ( newSerialNumber < MAX_SERIAL ) {
        newSerialNumber += 1;
        return;
    }
    newSerialNumber = 1;
    if ( newSeries[SECOND_LETTER] < 'Z' ) {
        newSeries[SECOND_LETTER] += 1;
        return;
    }
    if ( newSeries[FIRST_LETTER] < 'Z' ) {
        newSeries[SECOND_LETTER] = 'A';
        newSeries[FIRST_LETTER] += 1;
    return;
    }
}

string Passport::getSeries() const {
    return series;
}

int Passport::getSerialNumber() const {
    return serialNumber;
}

string Passport::getName() const {
    return name;
}

string Passport::getSurname() const {
    return surname;
}

Date Passport::getDate() const {
    return birthDate;
}

void Passport::setSeries(string series) {
    int last = SIZE - 1;

    for ( int i = 0; i < last; i++ ) {
        if ( series[i] >= 'a' && series[i] <= 'z' ) {
            series[i] = toupper(series[i]);
        }
    }

    Passport::validateSeries(series);

    for ( int i = 0; i < last; i++ ) {
        newSeries[i] = series[i];
    }
}

void Passport::setSeries(int serialNumber) {
    Passport::validateSerialNumber(serialNumber);

    newSerialNumber = serialNumber;
}

void Passport::setSeries(string series, int serialNumber) {
    int last = SIZE - 1;

    for ( int i = 0; i < last; i++ ) {
        if ( series[i] >= 'a' && series[i] <= 'z' ) {
            series[i] = toupper(series[i]);
        }
    }


    Passport::validate(series, serialNumber);

    for ( int i = 0; i < last; i++ ) {
        newSeries[i] = series[i];
    }
    newSerialNumber = serialNumber;
}

ostream& operator<<(ostream& out, const Passport& passport) {
    out.fill('0');
    out << "Passport: " << passport.getSeries() << setw(6) << passport.getSerialNumber() << endl;
    out << "Name: " << passport.getName() << endl;
    out << "Surname: " << passport.getSurname() << endl;
    out << passport.getDate();

    return out;
}
