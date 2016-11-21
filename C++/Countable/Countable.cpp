#include <iostream>
#include "Countable.h"

using namespace std;

int Countable::totalCount = 0;

Countable::Countable() {
    totalCount += 1;
}

Countable::~Countable() {
    totalCount -= 1;
}

int Countable::getTotalCount() {
    return totalCount;
}