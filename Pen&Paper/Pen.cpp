#include <iostream>
#include "Pen.h"
#include "Paper.h"

using namespace std;

Pen::Pen(int inkCapacity){
    this->inkCapacity = inkCapacity;
    this->inkAmount = 0;
}

Pen::~Pen(){}

int Pen::getInkAmount() const{
    return this->inkAmount;
}

int Pen::getInkCapacity() const{
    return this->inkCapacity;
}

void Pen::write(Paper& paper, const string& message){
    if ( inkAmount == 0 ) {
        throw OutOfInkException();
    }
    if ( inkAmount < message.length() ) {
        paper.addContent(message.substr(0, inkAmount-1));
        inkAmount = 0;

        return;
    }
    paper.addContent(message);
    inkAmount -= message.length();
}

void Pen::refill() {
    inkAmount = inkCapacity;
}


// Ручка.
// Каждая ручка имеет максимальный запас чернил (inkCapacity) и текущее количество чернил (inkAmount).
// Ручка пишет по бумаге. За каждый написанный символ из текущего количества чернил ручки списывается одна единица чернил. Без чернил ручка писать не может.
// Если в ручке недостаточно чернил, чтобы написать все сообщение, записывается столько символов, на сколько хватает чернил.
// Ручку можно перезаправить до максимального запаса чернил.