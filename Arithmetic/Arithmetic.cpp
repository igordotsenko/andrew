#include <iostream>
#include "Arithmetic.h"

using namespace std;

Arithmetic::Arithmetic(int first, int last, int step) {
    this->first = first;
    this->last = last;
    this->step = step;
    this->value = first;
    this->currentIndex = 1;

    if ( step == 0 ) {
        throw invalidStepException();
    }
}

Arithmetic::~Arithmetic() {}

void Arithmetic::next() {
    currentIndex += 1;
    if ( over() ) {
        return;
    }
    value += step;
}

void Arithmetic::prev() {
    currentIndex -= 1;
    if ( over() ) {
        return;
    }
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
    if ( index < 1 || index > last ) {
         throw InvalidIndexException();
    }

    return value = first + step * index - step;
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
    return value = first + step * currentIndex - step;
}
