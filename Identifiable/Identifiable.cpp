#include <iostream>
#include "Identifiable.h"

using namespace std;

int Identifiable::lastId = 0;

Identifiable::Identifiable() {
    lastId += 1;
    id = lastId;
}

Identifiable::~Identifiable() {}

int Identifiable::getIdentifiable() {
    return id;
}

// Identifiable
// Каждому новому экземпляру выдаётся уникальный идентификатор.