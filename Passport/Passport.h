#ifndef PASSPORT_H
#define PASSPORT_H

#include <iostream>
#include "Date.h"

#define SIZE 3

using namespace std;

class InvalidSerialException {};
class InvalidSerialNumberException {};

class Passport {
    private:
        static char newSeries[SIZE];
        static int newSerialNumber;

        char series[SIZE];
        int serialNumber;

        string name;
        string surname;
        Date date;

        static void validate(string series);
        static void validate(int serialNumber);

        void seriesGeneration();
    public:
        Passport(string name, string surname, int bDay, int bMonth, int bYear);
        // Passport(const Passport& other, std::string name, std::string surname, Date date);
        ~Passport();

        string getSeries() const;
        int getSerialNumber() const;
        string getName() const;
        string getSurname() const;
        Date getDate() const;

        static void setSeries(std::string series);
        static void setSeries(int serialNumber);
        static void setSeries(std::string series, int serialNumber);
};

ostream& operator<<(ostream& out, const Passport& passport);

#endif //PASSPORT_H

// Passport
// Каждый паспорт содержит шестизначный уникальный номер и двухбуквенную серию.
// Номера присваиваются автоматически.
// При достижении последнего шестизначного номера, серию следует изменить на следующую.
// Также можно в произвольный момент времени задать новую серию, при этом нумерация начинается с начала.
// При изменении серии можно задать новый стартовый номер.

// Каждый паспорт содержит имя, фамилию, год, месяц и день рождения.

// Предусмотрите возможные исключения.