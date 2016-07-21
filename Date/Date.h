#ifndef DATE_H
#define DATE_H

#include <iostream>

class InvalidDateException {
    public:
        std::string text;
        InvalidDateException(const std::string& text) : text(text) {}
};

class Date {
    private:
        int day;
        int month;
        int year;

        void validate(int day, int month, int year);
    public:
        Date(int day=1, int month=1, int year=1970);
        ~Date();

        int getDay() const;
        int getMonth() const;
        int getYear() const;
};

std::ostream& operator<<(std::ostream& out, const Date& date);

#endif //DATE_H