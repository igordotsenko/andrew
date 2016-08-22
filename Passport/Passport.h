#ifndef PASSPORT_H
#define PASSPORT_H

#include <iostream>
#include "Date.h"
#include <cctype>

#define SIZE 3

using namespace std;

class InvalidSerialException {};
class InvalidSerialNumberException {};
class SerialNumberIsAlreadyExcistException {};

class Passport {
    private:
        static char newSeries[];
        static int newSerialNumber;

        char series[SIZE];
        int serialNumber;

        string name;
        string surname;
        Date birthDate;

        static void validateSeries(string series);
        static void validateSerialNumber(int serialNumber);
        static void validate(string series, int serialNumber);
        
        void seriesGeneration();
    public:
        Passport(string name, string surname, int bDay, int bMonth, int bYear);
        ~Passport();

        string getSeries() const;
        int getSerialNumber() const;
        string getName() const;
        string getSurname() const;
        Date getDate() const;

        static void setSeries(string series);
        static void setSeries(int serialNumber);
        static void setSeries(string series, int serialNumber);
};

ostream& operator<<(ostream& out, const Passport& passport);

#endif //PASSPORT_H

