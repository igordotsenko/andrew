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
        paper.addContent(message.substr(0, inkAmount));
        inkAmount = 0;

        return;
    }
    paper.addContent(message);
    inkAmount -= message.length();
}

void Pen::refill() {
    inkAmount = inkCapacity;
}
