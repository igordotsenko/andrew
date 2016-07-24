#include <iostream>
#include <cstdlib>

using namespace std;

class OutOfMemoryException {};
class ZeroLenException {};

template <typename T>
class List {
    private:
        T* array;
        
        int capacity;
        double multiplier;
        int current;
    public:
        List(int capacity=100, double multiplier=1.05);
        ~List();

        int size() const;
        int max_size() const;

        void erase(int index);
        void insert(T value, int index);
        T find(T value) const;
        void push_back(T value);
        T pop_back();
        void sort();

        T operator[](int index) const;
        bool operator==(const List<T>& other) const;
        bool operator!=(const List<T>& other) const;
};

template <typename T>
List<T>::~List() {
    free(array);
}

template <typename T>
int List<T>::size() const {
    return current;
}

template <typename T>
int List<T>::max_size() const {
    return capacity;
}

template <typename T>
List<T>::List(int capacity, double multiplier) : capacity(capacity), current(0), multiplier(multiplier) {
    array = (T*) malloc(capacity * sizeof(T));

    if ( array == NULL ){
        throw OutOfMemoryException();
    }
}

template <typename T>
void List<T>::erase(int index) {
    if ( current == 0 ) {
        throw ZeroLenException();
    }

    if ( current > index && index > 0 ) {
        current -= 1;

        for ( int i = index; i < current; i++ ) {
            array[i] = array[i + 1];
        }
    }
}

template <typename T>
void List<T>::insert(T value, int index) {
    if ( current >= index && index >= 0 ) {
        push_back(value);

        for ( int j = current; j > index; j-- ) {
            array[j] = array[j - 1];
        }
        array[index] = value;
    }
}

template <typename T>
T List<T>::find(T value) const {
    for ( int i = 0; i < current; i++ ) {
        if ( array[i] == value ){
            return i;
        }
    }
    return -1;
}

template <typename T>
void List<T>::push_back(T value) {
    int newCurrent = current + 1;

    if ( newCurrent > capacity ){
        int newCapacity = capacity * multiplier;
        T* newArray = (T*)realloc(array, newCapacity * sizeof(T));

        if ( newArray == NULL ){
            throw OutOfMemoryException();
        }

        capacity = newCapacity;
        array = newArray;
    }
    array[current] = value;
    current = newCurrent;
}


template <typename T>
T List<T>::pop_back() {
    if ( current == 0 ) {
        throw ZeroLenException();
    }
    current -= 1;

    return array[current];
}

template <typename T>
void List<T>::sort() {
    int last = this->current - 1;
    
    for ( int i = 0; i < last; i++ ) {
        for ( int j = i; j < current; j++ ) {
            if ( array[j] < array[i] ) {
                T temp = array[i];
                
                array[i] = array[j];
                array[j] = temp;
            }
        }
    }
}

template <typename T>
T List<T>::operator[](int index) const {
    return array[index];
}


template <typename T>
bool List<T>::operator==(const List<T>& other) const {
    if ( current != other.size() ) {
        return false;
    }

    for ( int i = 0; i < current; i++ ) {
        if ( array[i] != other[i] ) {
            return false;
        }
    }

    return true;
}

template <typename T>
bool List<T>::operator!=(const List<T>& other) const {
    return current != other.current;
}

template <typename T>
ostream& operator<<(std::ostream& out, const List<T>& list) {
    T last = list.size() - 1;

    for ( int i = 0; i < last; i++ ) {
        out << list[i] << " ";
    }
    out << list[last];
    
    return out;
}