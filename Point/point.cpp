#include <iostream>
#include <cmath>

#include "Point.h"

using namespace std;

Point::Point(double x, double y) {
    this->x = x;
    this->y = y;
} 

Point::~Point() {}

double Point::getX() const {
    return x;
}

double Point::getY() const {
    return y;
}

void Point::setX(double x) {
    this->x = x;
}

void Point::setY(double y) {
    this->y = y;
}

double Point::distance(const Point& other) const {
    return hypot(other.x-x, other.y-y);
}

bool Point::operator==(const Point& other) const {
    return x == other.x && y == other.y;
}

bool Point::operator!=(const Point& other) const {
    return !(*this == other);
}

ostream& operator<<(ostream& out, const Point& other) {
    return out << '(' << other.getX() << ", " << other.getY() << ')' << endl;
}
