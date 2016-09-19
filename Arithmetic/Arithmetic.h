#ifndef ARITHMETIC_H
#define ARITHMETIC_H

#include <iostream>

using namespace std;

class InvalidIndexException {};
class OutOfBoundException {};

class Arithmetic {
    private:
        int first;
        int last;
        int diff;
        int value;
        int currentIndex;
        int lastIndex;

    public:
        Arithmetic(int first, int last, int diff);
        ~Arithmetic();

        bool over();
        void next();
        void prev();

        void resetToFirst();
        void resetToLast();

        int lastMember();
        int sum();

        int getValueAtIndex(int idnex);

        void operator++();
        void operator++(int);
        void operator--();
        void operator--(int);

        int operator*();
};

#endif //ARITHMETIC_H