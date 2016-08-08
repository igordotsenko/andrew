#ifndef IDENTIFIABLE_H
#define IDENTIFIABLE_H

#include <iostream>

using namespace std;

class Identifiable {
    private:
        static int lastId;
        int id;

    public:
        Identifiable();
        ~Identifiable();
        int getIdentifiable();
};

#endif //IDENTIFIABLE_H