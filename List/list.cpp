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
List<T>::~List(){
    free(array);
}

template <typename T>
int List<T>::size() const{
    return current;
}

template <typename T>
int List<T>::max_size() const{
    return capacity;
}

template <typename T>
List<T>::List(int capacity, double multiplier) : capacity(capacity), current(0), multiplier(multiplier){
    array = (T*) malloc(capacity * sizeof(T));

    if ( array == NULL ){
        throw OutOfMemoryException();
    }
}

template <typename T>
void List<T>::erase(int index){
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
void List<T>::insert(T value, int index){     //вставить 
    if ( current >= index && index >= 0 ) {
        push_back(value);

        for ( int i = current; i > index; i-- ) {
            array[i] = array[i - 1];
        }
        array[index] = value;
    }
}

template <typename T>
T List<T>::find(T value) const{
    for ( int i = 0; i < current; i++ ){
        if ( array[i] == value ){
            return i;
        }
    }
    return -1;
}

template <typename T>
void List<T>::push_back(T value){
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
T List<T>::pop_back(){
    if ( current == 0 ){
        throw ZeroLenException();
    }
    current -= 1;

    return array[current];
}

template <typename T>
void List<T>::sort(){
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
T List<T>::operator[](int index) const{
    return array[index];
}


template <typename T>
bool List<T>::operator==(const List<T>& other) const {
    return current == other.current;
}

template <typename T>
bool List<T>::operator!=(const List<T>& other) const {
    return current == other.current;
}

template <typename T>
ostream& operator<<(std::ostream& out, const List<T>& list){
    T last = list.size() - 1;

    for ( int i = 0; i < last; i++ ){
        out << list[i] << " ";
    }
    out << list[last];
    
    return out;
}

int main() {
    List<float>* array = new List<float>;
    float value, element;
    int index;

    cout << "Set size of array: ";
    cin >> element;

    cout << "Added  " << element << " elements: " << endl;
    for ( int i = 0; i < element; i++ ) {
        array->push_back(1.05*i);
    }
    cout << *array << endl;

    cout << "Enter erase index: ";
    cin >> index;
    cout << "erase "<< index <<" index: " << endl;
    array->erase(index);
    cout << *array << endl;

    cout << "Set value and insert index: ";
    cin >> value >> index;
    array->insert(value, index);
    cout << *array << endl;

    cout << "Find element: ";
    cin >> element;
    cout << "Index of element " << element << " is: " << array->find(element) << endl;

    array->sort();
    cout << "Array sorted: " << endl;
    cout << *array << endl;

    return 0;
}