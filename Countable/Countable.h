#ifndef COUNTABLE_H
#define COUNTABLE_H

#include <iostream>

using namespace std;

class Countable {
    private:
        static int totalCount;
    public:
        Countable();
        ~Countable();

        static int getTotalCount() ;
};

#endif //COUNTABLE_H