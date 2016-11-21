#ifndef MOHICAN_H
#define MOHICAN_H

#include <iostream>

using namespace std;

class NoLastMohicanException {};

class Mohican {
    private:
        static Mohican* lastMohican;
        Mohican* prev;
        string name;

    public:
        Mohican(string name);
        ~Mohican();

        static const Mohican& getLastMohican();
        const string& getName() const;
};

#endif //MOHICAN_H

// В любой момент времени можно получить последнего из могикан, без использования отдельного экземпляра.
// Подсказка: хранить указатель, возвращать ссылку.
