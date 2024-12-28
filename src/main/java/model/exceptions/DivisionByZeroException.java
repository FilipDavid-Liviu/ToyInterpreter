package model.exceptions;

public class DivisionByZeroException extends RuntimeException {
    String message;
    public DivisionByZeroException() {
        this.message = "DivisionByZeroException: Division by zero!";
    }
    public String getMessage() {
        return message;
    }
}
