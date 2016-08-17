#include <iostream>
#include <iomanip>
#include "Passport.h"

#define SIZE 3
#define MAX_SERIAL 999999

using namespace std;

char Passport::newSeries[SIZE] = {'A', 'A', 0};
int Passport::newSerialNumber = 1;

void Passport::validate(string series) {
    int last = SIZE - 1;

    if ( series.size() > last ) {
        throw InvalidSerialException();
    }
    for ( int i = 0; i < last; i++ ) {
        if ( series[i] < 'A' || series[i] > 'Z' ) {
            throw InvalidSerialException();
        }
    }
}

void Passport::validate(int serialNumber) {
    if ( serialNumber < 1 || serialNumber > MAX_SERIAL ) {
        throw InvalidSerialNumberException();
    }
}

void Passport::seriesGeneration() {
    for ( int i = 0; i < SIZE; i++ ) {
        this->series[i] = newSeries[i];
    }
    this->serialNumber = newSerialNumber;

    if ( newSerialNumber < MAX_SERIAL ) {
        newSerialNumber += 1;
    } else {
        newSerialNumber = 1;
        if ( newSeries[1] < 'Z' ) {
            newSeries[1] += 1;
        } else if ( newSeries[0] < 'Z' ) {
            newSeries[1] = 'A';
            newSeries[0] += 1;
        } else {
            throw InvalidSerialException();
        }
    }
}

Passport::Passport(string name, string surname, int bDay, int bMonth, int bYear) {
    this->name = name;
    this->surname = surname;
    this->date = Date(bDay, bMonth, bYear);

    this->seriesGeneration();
}

Passport::~Passport() {}

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
    return date;
}

void Passport::setSeries(string series) {
    Passport::validate(series);
    int last = SIZE - 1;

    for ( int i = 0; i < last; i++ ) {
        newSeries[i] = series[i];
    }
}

void Passport::setSeries(int serialNumber) {
    Passport::validate(serialNumber);

    newSerialNumber = serialNumber;
}

void Passport::setSeries(string series, int serialNumber) {
    Passport::validate(series);
    Passport::validate(serialNumber);
    int last = SIZE - 1;

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