package model.exceptions;

public class InvalidStatException extends IllegalArgumentException {
    public InvalidStatException(String msg) {
        System.out.println(msg);
    }
}
