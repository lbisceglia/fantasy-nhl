package model.exceptions;

public class InvalidPositionException extends IllegalArgumentException {
    public InvalidPositionException(String msg) {
        System.out.println(msg);
    }
}
