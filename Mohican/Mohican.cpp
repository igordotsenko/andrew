#include <iostream>
#include "Mohican.h"

using namespace std;

Mohican* Mohican::lastMohican = NULL;

Mohican::Mohican(string name) {
    this->name = name;

    if ( lastMohican == NULL ) {
        lastMohican = this;
        prev = NULL;
        current = NULL;
    } else {
        prev = lastMohican;
        lastMohican->current = this;
        lastMohican = this;
        current = NULL;
    }
}

Mohican::~Mohican() {
    if ( current == NULL ) {
        lastMohican = prev;
        prev->current = NULL;
    } else {
        prev->current = current;
        current->prev = prev;
    }
}

const Mohican& Mohican::getLastMohican() {
    return *lastMohican;
}

const string& Mohican::getName() const {
    return name;
}
