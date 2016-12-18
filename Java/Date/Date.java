public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) throws InvalidDateException {
        validate(day, month, year);

        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date() throws InvalidDateException {
        this.day = 1;
        this.month = 1;
        this.year = 1970;
    }

    public String toString() {
        StringBuffer out = new StringBuffer();

        out.append(getDay()+".");
        out.append(getMonth()+".");
        out.append(getYear()+"\n");

        return out.toString();
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    private void validate(int day, int month, int year) throws InvalidDateException {
        int daysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int indexMonth = month - 1;

        if ( year % 400 == 0 || year % 4 == 0 && year % 100 != 0 ) {
            daysInMonth[1] += 1;
        }
        if ( month < 1 || month > 12 ) {
            throw new InvalidDateException();
        }
        if ( day > daysInMonth[indexMonth] || day < 1 ) {
            throw new InvalidDateException();
        }
    }
}