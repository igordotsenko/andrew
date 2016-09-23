#ifndef ARITHMETIC_H
#define ARITHMETIC_H

#include <iostream>

class InvalidStepException {};
class InvalidIndexException {};
class OutOfBoundException {};

class Arithmetic {
    private:
        int first;
        int last;
        int step;
        int currentIndex;

    public:
        Arithmetic(int first, int last, int step);
        ~Arithmetic();

        void next();
        void prev();
        bool over();

        void resetToFirst();
        void resetToLast();

        int lastMember();
        int sum();

        int getIndex();
        int getValue();
        int getValueAtIndex(int idnex);

        void operator++();
        void operator++(int);
        void operator--();
        void operator--(int);

        int operator*();
};

#endif //ARITHMETIC_H