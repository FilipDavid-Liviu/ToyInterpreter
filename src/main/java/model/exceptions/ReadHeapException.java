package model.exceptions;

public class ReadHeapException extends RuntimeException {
    String message;
    public ReadHeapException() {
        this.message = "ReadHeapException: The expression is not a reference type!";
    }
    public String getMessage() {
        return message;
    }
}
