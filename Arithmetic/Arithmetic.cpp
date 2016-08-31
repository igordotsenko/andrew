#include <iostream>
#include "Arithmetic.h"

using namespace std;

Arithmetic::Arithmetic(int first, int last, int diff): first(first), last(last), diff(diff), value(first), currentIndex(1) {

}

Arithmetic::~Arithmetic() {}

bool Arithmetic::over() {
    return currentIndex > last;
}

void Arithmetic::next() {
    if ( over() ) {
        return;
    }
    currentIndex += 1;
    value += diff;
}

bool Arithmetic::less() {
    return currentIndex < first;
}

void Arithmetic::prev() {
    if ( less() ) {
        return;
    }
    currentIndex -= 1;
    value -= diff;
}

int Arithmetic::lastMember() {
    return value = first + diff * (last - 1);
}

int Arithmetic::summary() {
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
