#include <iostream>
#include "Mohican.h"

using namespace std;

Mohican* Mohican::lastMohican = NULL;

Mohican::Mohican(string name) {
    this->name = name;

    prev = lastMohican;
    lastMohican = this;
}

Mohican::~Mohican() {
    if ( lastMohican != NULL ) {
        lastMohican = prev;
    }
}

const Mohican& Mohican::getLastMohican() {
    if ( lastMohican == NULL ) {
        throw NoLastMohicanException();
    }

    return *lastMohican;
}

const string& Mohican::getName() const {
    return name;
}

