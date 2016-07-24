#include <iostream>
#include <cmath>

#include "Vector.h"

using namespace std;

Vector::Vector(double x, double y) {
    this->x = x;
    this->y = y;
}

Vector::~Vector() {}

double Vector::getX() const {
    return x;
}

double Vector::getY() const {
    return y;
}

void Vector::setX(double value) {
    this->x = x;
}

void Vector::setY(double value) {
    this->y = y;
}

double Vector::len(Vector& v) const {
    return hypot(v.x, v.y);
}

bool Vector::operator==(const Vector& other) const {
    return x == other.x && y == other.y;
}

bool Vector::operator!=(const Vector& other) const {
    return *this != other;
}

void Vector::operator+=(const Vector& other) {
    x += other.x;
    y += other.y;
}

void Vector::operator-=(const Vector& other) {
    x -= other.x;
    y -= other.y;
}

Vector Vector::operator+(const Vector& other) const {
    Vector sum;

    sum.x = this->x + other.x;
    sum.y = this->y + other.y;

    return sum;
}

Vector Vector::operator-(const Vector& other) const {
    Vector diff;

    diff.x = this->x - other.x;
    diff.y = this->y - other.y;

    return diff;
}

ostream& operator<<(ostream& out, const Vector& other) {
    return out << '(' << other.getX() << ", " << other.getY() << ')';
}