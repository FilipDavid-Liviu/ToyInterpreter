package model.exceptions;

public class VariableAlreadyDeclaredException extends RuntimeException {
    String message;
    public VariableAlreadyDeclaredException(String id) {
        message = "VariableAlreadyDeclaredException: Variable \"" + id + "\" already declared!";
    }

    public String getMessage() {
        return message;
    }
}
