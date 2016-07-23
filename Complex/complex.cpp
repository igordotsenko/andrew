#include <iostream>
#include <cmath>

#include "Complex.h"

using namespace std;

Complex::Complex(double real, double imaginary) {
    this->real = real;
    this->imaginary = imaginary;
}

Complex::~Complex() {}

double Complex::getReal() const {
    return real;
}

double Complex::getImaginary() const {
    return imaginary;
}

bool Complex::operator==(const Complex& other) const {
    return real == other.real && imaginary == other.imaginary;
}

bool Complex::operator!=(const Complex& other) const {
    return *this != other;
}

void Complex::operator+=(const Complex& other) {
    real += other.real;
    imaginary += other.real;
}

void Complex::operator-=(const Complex& other) {
    real -= other.real;
    imaginary -= other.real;
}

Complex Complex::operator+(const Complex& other) const {
    Complex result = *this;

    result.real += other.real;
    result.imaginary += other.imaginary;
    
    return result;
}


Complex Complex::operator-(const Complex& other) const {
    Complex result = *this;

    result.real -= other.real;
    result.imaginary -= other.imaginary;

    return result;
}

Complex Complex::operator*(const Complex& other) const {
    Complex result = *this;

    result.real *= other.real;
    result.imaginary *= other.imaginary;

    return result;
    }

std::ostream& operator<<(std::ostream& out, const Complex& complex) {
    if ( complex.getImaginary() < 0 ) {
        return out << complex.getReal() << complex.getImaginary();
    }

    return out << complex.getReal() << '+' << complex.getImaginary();
}
