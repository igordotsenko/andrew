#include <iostream>
#include "Arithmetic.h"

using namespace std;

Arithmetic::Arithmetic(int first, int last, int diff) {
    this->first = first;
    this->last = last;
    this->diff = diff;
    this->value = first;
    this->currentIndex = 1;
}

Arithmetic::~Arithmetic() {}

bool Arithmetic::over() {
    return currentIndex > last || currentIndex < first;
}

void Arithmetic::next() {
    currentIndex += 1;
    if ( over() ) {
        return;
    }
    value += diff;
}

void Arithmetic::prev() {
    currentIndex -= 1;
    if ( over() ) {
        return;
    }
    value -= diff;
}

void Arithmetic::resetToFirst() {
    currentIndex = first; 
}

void Arithmetic::resetToLast() {
    currentIndex = last;
}

int Arithmetic::lastMember() {
    return value = first + diff * (last - 1);
}

int Arithmetic::sum() {
    return value = ((first + value) * last) / 2;
}

int Arithmetic::getValueAtIndex(int index) {
    int newValue = first + diff * index - diff;

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
