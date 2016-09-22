
#include <iostream>
#include "Arithmetic.h"

using namespace std;

Arithmetic::Arithmetic(int first, int last, int step) {
    this->first = first;
    this->last = last;

    if ( step == 0 ) {
        throw invalidStepException();
    }

    this->step = step;
    this->value = first;
    this->currentIndex = 1;

}

Arithmetic::~Arithmetic() {}

void Arithmetic::next() {
    if ( over() ) {
        throw OutOfBoundException();
    }
    currentIndex += 1;
    value += step;
}

void Arithmetic::prev() {
    if ( over() ) {
        throw OutOfBoundException();
    }
    currentIndex -= 1;
    value -= step;
}

bool Arithmetic::over() {
    return currentIndex > last || currentIndex < 1;
}

void Arithmetic::resetToFirst() {
    currentIndex = 1; 
}

void Arithmetic::resetToLast() {
    currentIndex = last;
    value = lastMember();
}

int Arithmetic::lastMember() {
    return value = first + step * (last - 1);
}

int Arithmetic::sum() {
    return value = ((first + value) * last) / 2;
}

int Arithmetic::getIndex() {
    return currentIndex;
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
    return value;
}