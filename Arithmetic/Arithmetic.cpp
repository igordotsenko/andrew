#include <iostream>
#include "Arithmetic.h"

using namespace std;

Arithmetic::Arithmetic(int first, int last, int step) {
    if ( step == 0 ) {
        throw InvalidStepException();
    }
    this->first = first;
    this->last = last;
    this->step = step;
    this->currentIndex = 1;
}

Arithmetic::~Arithmetic() {}

void Arithmetic::next() {
    if ( over() ) {
        throw OutOfBoundException();
    }
    currentIndex += 1;
}

void Arithmetic::prev() {
    if ( over() ) {
        throw OutOfBoundException();
    }
    currentIndex -= 1;
}

bool Arithmetic::over() {
    return currentIndex > last || currentIndex < 1;
}

void Arithmetic::resetToFirst() {
    currentIndex = 1; 
}

void Arithmetic::resetToLast() {
    currentIndex = last;
}

int Arithmetic::lastMember() {
    int lastMember = first + step * (last - 1);

    return lastMember;
}

int Arithmetic::sum() {
    int sum = ((first + lastMember()) / 2) * last;

    return sum;
}

int Arithmetic::getIndex() {
    return currentIndex;
}

int Arithmetic::getValue() {
    return first + step * (currentIndex - 1);
}

int Arithmetic::getValueAtIndex(int index) {
    int newValue = first + step * index - step;

    if ( index < 1 || index > last ) {
         throw InvalidIndexException();
    }

    return newValue;
}

void Arithmetic::operator++() {
    next();
}

void Arithmetic::operator++(int) {
    operator++();
}

void Arithmetic::operator--() {
    prev();
}

void Arithmetic::operator--(int) {
    operator--();
}

int Arithmetic::operator*() {
    return getValue();
}