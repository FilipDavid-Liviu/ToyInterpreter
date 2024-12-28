package model.exceptions;

public class StackEmptyException extends RuntimeException {
    String message;
    public StackEmptyException() {
        message = "StackEmptyException: Stack is empty! Nothing to pop.";
    }
    public String getMessage() {
        return message;
    }
}
