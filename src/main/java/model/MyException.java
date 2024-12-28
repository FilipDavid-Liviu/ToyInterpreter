package model;

public class MyException extends RuntimeException {
    String message;
    public MyException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
