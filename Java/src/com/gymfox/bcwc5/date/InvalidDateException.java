/**
 * Created by andrew on 03.12.16.
 */
public class InvalidDateException extends Exception {
    public String text;

    public InvalidDateException(String text) {
        this.text = text;
    }
}

