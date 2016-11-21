#include <iostream>
#include "Paper.h"
#include "Pen.h"

using namespace std;

int main(){
    Pen* pen = new Pen();
    Paper* paper = new Paper();

    pen->refill();
    cout << "Symbols on paper: " << paper->getSymbols() << endl;
    cout << "MaxSymbols of paper: " << paper->getMaxSymbols() << endl;
    cout << "Ink in Pen: " << pen->getInkAmount() << endl;

    pen->write(*paper, "1234567890");

    paper->show();

    cout << "Symbols on paper after writing: " << paper->getSymbols() << endl;
    cout << "MaxSymbols of paper after: " << paper->getMaxSymbols() << endl;
    cout <<  "Ink amount after writing: " << pen->getInkAmount() << endl;




    return 0;
}