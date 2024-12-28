package model.exceptions;

import java.util.Objects;

public class InvalidOperationException extends RuntimeException {
    String message;
    public InvalidOperationException(String type, String operation) {
        this.message = "InvalidOperationException: Cannot compose two " + type + " types using operation " + operation;
    }
    public InvalidOperationException() {
        this.message = "InvalidOperationException: Two different types cannot be composed";
    }
    public InvalidOperationException(String message) {
        if (Objects.equals(message, "reference")) {
            this.message = "InvalidOperationException: Cannot compose two reference types";
        }
        else this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
