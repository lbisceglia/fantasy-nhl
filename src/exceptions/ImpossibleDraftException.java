package exceptions;

public class ImpossibleDraftException extends Exception {
    String msg;

    // Construct an ImpossibleDraftException with the given error message
    public ImpossibleDraftException(String msg) {
        this.msg = msg;
    }

    // EFFECTS: Return the exception's message
    public String getMsg() {
        return msg;
    }
}
