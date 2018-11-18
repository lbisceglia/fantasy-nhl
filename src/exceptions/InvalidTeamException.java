package exceptions;

public class InvalidTeamException extends Exception {
    private String msg;

    // EFFECTS: Constructs an InvalidTeamException with the given message
    public InvalidTeamException(String msg) {
        this.msg = msg;
    }

    // EFFECTS: gets the exception's message
    public String getMsg() {
        return msg;
    }
}
