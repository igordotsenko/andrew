#include <iostream>
#include "Paper.h"

using namespace std;

Paper::Paper(int maxSymbols): maxSymbols(maxSymbols), symbols(0), content(""){

}

Paper::~Paper(){

}

int Paper::getMaxSymbols() const{
    return this->maxSymbols;
}

int Paper::getSymbols() const{
    return this->symbols;
}

void Paper::addContent(const string& message){
    int total = content.length() + message.length();

    if ( total > maxSymbols ) {
        content += message.substr(0, maxSymbols - 1);
        symbols = maxSymbols;
        throw OutOfSpaceException();
    }
    content += message;
    symbols = total;
}

void Paper::show() const{
    cout << content << endl;
}

// Бумага.
// Каждый лист бумаги может вместить определенное количество символов (maxSymbols). Так же, в произвольный момент времени можно узнать, сколько символов уже написано на листе (symbols).
// На чистом листе бумаги нет ни одного символа. КО.
// Если на лист бумаги невозможно поместить все сообщение, необходимо записать столько символов, сколько может вместить лист.
// На заполненный лист бумаги невозможно ничего записать.
// Всегда есть возможность прочитать текст, написанный на листе бумаги.