#ifndef ARITHMETIC_H
#define ARITHMETIC_H

#include <iostream>

using namespace std;

class InvalidIndexException {};

class Arithmetic {
    private:
        int first;
        int last;
        int diff;
        int value;
        int currentIndex;

    public:
        Arithmetic(int first, int last, int diff);
        ~Arithmetic();

        bool over();
        void next();
        bool less();
        void prev();

        int lastMember();
        int summary();

        int getValueAtIndex(int idnex);

        void operator++();
        void operator++(int);
        void operator--();
        void operator--(int);

        int operator*();
};

#endif //ARITHMETIC_H